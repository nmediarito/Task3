package com.example.biservice.service;


import com.example.biservice.model.KafkaEvent;
import com.example.biservice.model.OrderQuantity;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.common.utils.Bytes;

import java.util.function.Function;

@Configuration
public class StreamProcessing {

    public final static String PRODUCT_STORE = "product-store";

    @Bean
    public Function<KStream<?, KafkaEvent>, KStream<String, OrderQuantity>> process() {
        return inputStream -> {
            KTable<String, Long> orderKTable = inputStream.
                    mapValues(KafkaEvent::getProductName).
                    groupBy((keyIgnored, value) -> value).
                    count(
                            Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(PRODUCT_STORE).
                                    withKeySerde(Serdes.String()).
                                    withValueSerde(Serdes.Long())
                    );

            KStream<String, OrderQuantity> orderQuantity = orderKTable.
                    toStream().
                    map((k, v) -> KeyValue.pair(k, new OrderQuantity(k, v)));
            // use the following code for testing
            orderQuantity.print(Printed.<String, OrderQuantity>toSysOut().withLabel("TEST"));

            return orderQuantity;

        };
    }

}
