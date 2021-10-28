package com.example.biservice.controller;

import com.example.biservice.service.InteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BiServiceController {

    @Autowired
    InteractiveQuery interactiveQuery;

    //gets total order quantity of a product
    @GetMapping("/orderQuantity/{productName}")
    long getProductQuantityByName(@PathVariable String productName) {
        return interactiveQuery.getOrderQuantity(productName);
    }

    //gets the names of all the products
    @GetMapping("/products/all")
    List<String> getAllProducts() {
        return interactiveQuery.getAllProducts();
    }

    //gets total order quantity of all the products
    @GetMapping("/productsQuantity/all")
    List<Integer> getProductsWithQuantity() {
        return interactiveQuery.getAllProductTotals();
    }

    //REST API to get the all the orders of a customer
    @GetMapping("/customerOrders/{customerId}")
    ResponseEntity<?> getOrdersByCustomer(@PathVariable Long customerId) {
        return interactiveQuery.getOrdersByCustomer(customerId);
    }

    //REST API to get the all the products ordered by a customer
    @GetMapping("/customerOrdersProducts/{customerId}")
    ResponseEntity<?> getProductsOrdered(@PathVariable Long customerId) {
        return interactiveQuery.getProductsOrdered(customerId);
    }

    //REST API to get the all the total prices of each orders made by a customer
    @GetMapping("/customerOrderTotals/{customerId}")
    ResponseEntity<?> getOrderTotals(@PathVariable Long customerId) {
        return interactiveQuery.getOrderTotals(customerId);
    }

}
