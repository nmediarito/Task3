package com.example.biservice.controller;

import com.example.biservice.model.OrderQuantity;
import com.example.biservice.service.InteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BiServiceController {

    @Autowired
    InteractiveQuery interactiveQuery;

    @GetMapping("/orderQuantity/{productName}")
    long getProductQuantityByName(@PathVariable String productName) {
        return interactiveQuery.getOrderQuantity(productName);
    }

    /*
    @GetMapping("/ordersByCustomer/{id}")
    List<Integer> getCustomerOrders(@PathVariable Long id) {
        return interactiveQuery.getOrdersByCustomer();
    }
     */

    @GetMapping("/products/all")
    List<String> getAllProducts() {
        return interactiveQuery.getAllProducts();
    }

    @GetMapping("/productsQuantity/all")
    List<Integer> getProductsWithQuantity() {
        return interactiveQuery.getTotalPerProduct();
    }

}
