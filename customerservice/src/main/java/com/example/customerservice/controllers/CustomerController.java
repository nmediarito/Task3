package com.example.customerservice.controllers;

import com.example.customerservice.entities.Customer;
import com.example.customerservice.repositories.CustomerRepository;
import com.example.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository){
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer findCustomer(@PathVariable Long id){
        return customerService.findCustomer(id);
    }

    @GetMapping("/customersValidate/{id}")
    public boolean validateCustomer(@PathVariable Long id){
        return customerService.validateID(id);
    }

    @PostMapping("/customers")
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
        return customerService.changeCustomer(newCustomer, id);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }


}
