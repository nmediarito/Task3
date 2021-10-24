package com.example.biservice.service;

import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

@Service
public class InteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    public InteractiveQuery(InteractiveQueryService interactiveQueryService){
        this.interactiveQueryService = interactiveQueryService;
    }

    public int getOrderQuantity(String productName){
        //get the total order quantity
        return 0;
    }

}
