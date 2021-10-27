package com.example.biservice.service;

import com.example.biservice.model.OrderQuantity;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InteractiveQuery {

    private InteractiveQueryService interactiveQueryService;

    public InteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public long getOrderQuantity(String productName) {
        if (productStore().get(productName) != null) {
            return productStore().get(productName);
        } else {
            throw new NoSuchElementException();
        }
    }

    /*
    public long getOrdersByCustomer(Long id) {
        if (productStore().get(id) != null) {
            return productStore().get(id);
        } else {
            throw new NoSuchElementException();
        }
    }
     */

    public List<String> getAllProducts() {
        List<String> productList = new ArrayList<>();
        KeyValueIterator<String, Long> all = productStore().all();
        while (all.hasNext()) {
            String nextKey = all.next().key;
            productList.add(nextKey);
        }
        return productList;
    }

    public List<Integer> getTotalPerProduct() {
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

}
