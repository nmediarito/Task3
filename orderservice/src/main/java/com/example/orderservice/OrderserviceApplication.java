package com.example.orderservice;

import com.example.orderservice.entities.KafkaEvent;
import com.example.orderservice.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class OrderserviceApplication{

	private static final Logger logger = LoggerFactory.getLogger(OrderserviceApplication.class);
	private static final String url = "http://localhost:8081/createOrder";
	private OrderService orderService;

	public OrderserviceApplication(OrderService orderService){
		this.orderService = orderService;
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(StreamBridge streamBridge) throws Exception {

		return args -> {

			logger.info("EXECUTING : COMMAND LINE RUNNER");
			int min = 1;
			int max = 5;
			int randomQuantity;
			int randomCustomer;
			String[] products = new String[] {"", "APPLE", "PEAR", "BEEF", "ORANGE", "BANANA"};

			while(!Thread.currentThread().isInterrupted()){
				//randomize values for the order
				randomQuantity = (int) Math.floor(Math.random() * (max-min+1) + min);
				randomCustomer = (int) Math.floor(Math.random() * (max-min+1) + min);
				//create an order every 2 seconds
				KafkaEvent kafkaEvent = orderService.createOrder((long) randomCustomer, products[randomQuantity], randomQuantity);
				//connects to a topic and sends message into order-outbound
				streamBridge.send("order-outbound", kafkaEvent);
				logger.info("ORDER EXECUTED : COMMAND LINE RUNNER");
				Thread.sleep(1200);
			}

		};
	}

}
