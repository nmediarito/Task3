package com.example.biservice.controller;

import com.example.biservice.service.InteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiServiceController {

    @Autowired
    InteractiveQuery interactiveQuery;

    @GetMapping("/order/{productName}/quantity")
    int getOrderQuantityPerProduct(@PathVariable String productName){
        return interactiveQuery.getOrderQuantity(productName);
    }


}
