package com.example.biservice.service;

import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    public OrderInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public long getOrderQuantity(String orderName) {
        if (orderStore().get(orderName) != null) {
            return orderStore().get(orderName);
        } else {
            throw new NoSuchElementException(); //TODO: should use a customised exception.
        }
    }

    private ReadOnlyKeyValueStore<String, Long> orderStore() {
        return this.interactiveQueryService.getQueryableStore(ApplianceStreamProcessing.ORDER_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

}
