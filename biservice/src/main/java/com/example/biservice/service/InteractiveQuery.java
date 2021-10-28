package com.example.biservice.service;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class InteractiveQuery {

    private RestTemplate restTemplate;
    private InteractiveQueryService interactiveQueryService;

    public InteractiveQuery(InteractiveQueryService interactiveQueryService, RestTemplateBuilder restTemplate) {
        this.interactiveQueryService = interactiveQueryService;
        this.restTemplate = restTemplate.build();
    }

    public long getOrderQuantity(String productName) {
        if (productStore().get(productName) != null) {
            return productStore().get(productName);
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<String> getAllProducts() {
        List<String> productList = new ArrayList<>();
        KeyValueIterator<String, Long> all = productStore().all();
        while (all.hasNext()) {
            String nextKey = all.next().key;
            productList.add(nextKey);
        }
        return productList;
    }

    public List<Integer> getAllProductTotals() {
        List<Integer> totals = new ArrayList<>();
        KeyValueIterator<String, Long> all = productStore().all();
        while (all.hasNext()) {
            long nextKey = all.next().value;
            totals.add((int) nextKey);
        }
        return totals;
    }

    private ReadOnlyKeyValueStore<String, Long> productStore() {
        return this.interactiveQueryService.getQueryableStore(StreamProcessing.PRODUCT_STORE,
                QueryableStoreTypes.keyValueStore());
    }

    public ResponseEntity<?> getOrdersByCustomer(Long customerId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        // make an HTTP GET request
        ResponseEntity<?> orders = restTemplate.exchange("http://localhost:8081/customerOrders/" + customerId, HttpMethod.GET, request, Object.class);
        return orders;
    }

    public ResponseEntity<?> getProductsOrdered(Long customerId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        // make an HTTP GET request
        ResponseEntity<?> orders = restTemplate.exchange("http://localhost:8081/customerOrdersProducts/" + customerId, HttpMethod.GET, request, Object.class);
        return orders;
    }

    public ResponseEntity<?> getOrderTotals(Long customerId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        // make an HTTP GET request
        ResponseEntity<?> orders = restTemplate.exchange("http://localhost:8081/customerOrderTotals/" + customerId, HttpMethod.GET, request, Object.class);
        return orders;
    }

}
