package com.example.orderservice.controllers;

import com.example.orderservice.entities.Customer;
import com.example.orderservice.entities.KafkaEvent;
import com.example.orderservice.entities.Order;
import com.example.orderservice.entities.Product;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService){
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping("/createOrder")
    public void createOrder(@RequestParam(value="id") Long id, @RequestParam(value="productName") String productName, @RequestParam(value="quantity") int quantity){
        orderService.createOrder(id, productName, quantity);
    }

    @GetMapping("/findCustomerByOrder/{orderId}")
    public Customer findCustomerByOrder(@PathVariable Long orderId){
        return orderService.findCustomerByOrder(orderId);
    }

    @GetMapping("/findProductByOrder/{orderId}")
    public List<Product> findProductByOrder(@PathVariable Long orderId){
        return orderService.findProductByOrder(orderId);
    }

}
