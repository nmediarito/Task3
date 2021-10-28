package com.example.orderservice.services;

import com.example.orderservice.entities.Customer;
import com.example.orderservice.entities.KafkaEvent;
import com.example.orderservice.entities.Order;
import com.example.orderservice.entities.Product;
import com.example.orderservice.events.OrderEventPublisher;
import com.example.orderservice.repositories.CustomerRepository;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class OrderService {

    private RestTemplate restTemplate;
    private OrderRepository orderRepository;
    private OrderEventPublisher publisher;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public OrderService(){

    }

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderEventPublisher publisher, RestTemplateBuilder restTemplate){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.publisher = publisher;
        this.restTemplate = restTemplate.build();
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public KafkaEvent createOrder(@PathVariable Long id, @PathVariable String pName, @PathVariable int quantity){

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        KafkaEvent kafkaEvent = new KafkaEvent();
        // make an HTTP GET request
        ResponseEntity<Boolean> validateID = restTemplate.exchange("http://localhost:8082/customersValidate/" + id, HttpMethod.GET, request, Boolean.class);

        if(validateID.getBody()){
            ResponseEntity<Integer> checkInventory = restTemplate.exchange("http://localhost:8080/getInventory/" + pName , HttpMethod.GET, request, Integer.class);
            //check the stock quantity using product service
            System.out.println("UNIT PRICE: $" + checkInventory.getBody());
            kafkaEvent.setPrice(checkInventory.getBody());
            //save in orders table and orders_products table after checks
            if(saveOrder(pName, id, quantity)){
                //create an event of an order for Kafka
                kafkaEvent.setCustomerID(id);
                kafkaEvent.setProductName(pName);
                kafkaEvent.setQuantity(quantity);
                System.out.println(kafkaEvent.toString());
                System.out.println("ORDER CREATED SUCCESSFULLY");
                //raise a domain event for the order
                publisher.publishEvent("CREATING ORDER", pName, quantity);
                return kafkaEvent;
            }
        } else {
            throw new IllegalStateException("CUSTOMER ID " + id + " DOES NOT EXIST");
        }
        return null;
    }

    public boolean saveOrder(String productName, Long id, int quantity){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        Product product = restTemplate.getForObject("http://localhost:8080/productByName/" + productName, Product.class);
        //if the quantity the user asks for is more than the current stock of the product
        if(quantity > product.getStockQuantity()){
            throw new IllegalStateException("YOU HAVE ORDERED OVER THE CURRENT STOCK QUANTITY OF THE PRODUCT");
        } else if (product.getStockQuantity() == 0){
            throw new IllegalStateException("OUT OF STOCK");
        } else {
            //create order
            ResponseEntity<Object> checkQuantity = restTemplate.exchange("http://localhost:8080/productByName/" + productName , HttpMethod.GET, request, Object.class);
            Order order = new Order();
            order.setQuantity(quantity);
            order.setSupplier("NA");
            Customer customer;
            boolean customerExists = customerRepository.existsById(id);
            //if customer already has ordered, then use that existing customer from the database
            if(customerExists){
                customer = customerRepository.findById(id).orElse(null);
            } else {
                //create an entry of the customer creating an order
                customer = restTemplate.getForObject("http://localhost:8082/customers/" + id, Customer.class);
                customerRepository.save(customer);
            }
            order.setCustomer(customer);
            order.getProducts().add(product);
            productRepository.save(product);
            orderRepository.save(order);
            return true;
        }

    }


    @GetMapping("/findCustomerByOrder/{orderId}")
    public Customer findCustomerByOrder(@PathVariable Long orderId){
        return orderRepository.findById(orderId).get().getCustomer();
    }

    @GetMapping("/findProductByOrder/{orderId}")
    public List<Product> findProductByOrder(@PathVariable Long orderId){
        return orderRepository.findById(orderId).get().getProducts();
    }

    public List<Order> findOrdersByCustomer(Long customerId){
        //empty list product
        List<Order> products = new ArrayList<>();
        //get the customer
        Customer customer = customerRepository.findById(customerId).orElse(null);
        //loop through the customers orders
        for(int i = 0; i < customer.getOrders().size(); i++){
            int totalPrice = customer.getOrders().get(i).getQuantity();
            for(int j = 0; j < customer.getOrders().get(i).getProducts().size(); j++){
                //multiply the quantity within the order with the price of each individual product within the order
                totalPrice = totalPrice * customer.getOrders().get(i).getProducts().get(j).getPrice();
                //sets the total price of the order
                customer.getOrders().get(i).setTotalOrderPrice(totalPrice);
            }
            //add the total order value into the specific product from the order of the customer
            products.add(customer.getOrders().get(i));
        }
        //return the list of products per order of the customer
        return products;
    }

    public List<String> findProductsByCustomer(Long customerId){
        //empty list product
        List<String> productNames = new ArrayList<>();
        //get the customer
        Customer customer = customerRepository.findById(customerId).orElse(null);
        //loop through the customers orders
        for(int i = 0; i < customer.getOrders().size(); i++){
            for(int j = 0; j < customer.getOrders().get(i).getProducts().size(); j++){
                //add the product names ordered by the customer
                productNames.add(customer.getOrders().get(i).getProducts().get(j).getName());
            }
        }
        //return the list of products per order of the customer
        return productNames;
    }

    public Map<Long, Integer> findTotalsByCustomer(Long customerId){
        //empty list product
        Map<Long, Integer> totals = new HashMap<>();
        //get the customer
        Customer customer = customerRepository.findById(customerId).orElse(null);
        //loop through the customers orders
        for(int i = 0; i < customer.getOrders().size(); i++){
            int totalPrice = customer.getOrders().get(i).getQuantity();
            for(int j = 0; j < customer.getOrders().get(i).getProducts().size(); j++){
                //multiply the quantity within the order with the price of each individual product within the order
                totalPrice = totalPrice * customer.getOrders().get(i).getProducts().get(j).getPrice();
                //sets the total price of the order
                customer.getOrders().get(i).setTotalOrderPrice(totalPrice);
                //put the key = order id and value = total price of the order
                totals.put(customer.getOrders().get(i).getId() ,customer.getOrders().get(i).getTotalOrderPrice());
            }
        }
        //return the list of products per order of the customer
        return totals;
    }

}
