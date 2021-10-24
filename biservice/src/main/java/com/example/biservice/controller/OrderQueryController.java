package com.example.biservice.controller;


import com.example.biservice.service.OrderInteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderQueryController {

    @Autowired
    OrderInteractiveQuery orderInteractiveQuery;

    @GetMapping("/order/{orderName}/quantity")
    long getBrandQuantityByName(@PathVariable String brandName) {
        return orderInteractiveQuery.getOrderQuantity(brandName);
    }
}
