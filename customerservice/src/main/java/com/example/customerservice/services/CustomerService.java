package com.example.customerservice.services;


import com.example.customerservice.entities.Customer;
import com.example.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer){
        //print new customer
        System.out.println(customer);
        customerRepository.save(customer);
    }

    public Customer changeCustomer(Customer newCustomer, Long id){
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setCompanyName(newCustomer.getCompanyName());
                    customer.setAddress(newCustomer.getAddress());
                    customer.setCountry(newCustomer.getCountry());
                    return customerRepository.save(customer);
                }).orElseGet(() -> {
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }

    public void deleteCustomer(Long customerId){
        boolean exists = customerRepository.existsById(customerId);
        if(!exists){
            throw new IllegalStateException(customerId + " does not exist");
        } else {
            customerRepository.deleteById(customerId);
            System.out.println(customerId + " has been deleted");
        }
    }

    public boolean validateID(@PathVariable Long id){
        //checks if customer id exists
        boolean idValid = customerRepository.existsById(id);
        if(idValid){
            Optional<Customer> customer = customerRepository.findById(id);
            System.out.println("CUSTOMER ADDRESS: " + customer.get().getAddress() + "\n" + "CUSTOMER CONTACTS");
            for(int i = 0; i < customer.get().getContacts().size(); i++){
                System.out.println(customer.get().getContacts().get(i).getPhone());
            }
            return true;
        } else {
            System.out.println("Customer " + id + " does not exist");
            return false;
        }

    }

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

}
