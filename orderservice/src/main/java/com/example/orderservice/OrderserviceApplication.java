package com.example.orderservice;

import com.example.orderservice.entities.Order;
import org.springframework.boot.CommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderserviceApplication {
	private static final Logger logger = LoggerFactory.getLogger(OrderserviceApplication.class);
	private static final String url = "http://localhost:8081/createOrder";


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
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> map;
			HttpEntity<MultiValueMap<String, Object>> request;
			ResponseEntity<Order> response;
			int min = 1;
			int max = 5;
			int randomQuantity;
			int randomCustomer;

			while(!Thread.currentThread().isInterrupted()){
				//randomize values for the order
				randomQuantity = (int) Math.floor(Math.random() * (max-min+1) + min);
				randomCustomer = (int) Math.floor(Math.random() * (max-min+1) + min);
				map = new LinkedMultiValueMap<String, Object>();
				map.add("id", randomCustomer);
				map.add("productName", "APPLE");
				map.add("quantity", randomQuantity);
				request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
				//gets the new Order object from the post request
				response = restTemplate.postForEntity(url, request, Order.class);
				//binder name "appliance-outbound" is defined in the application.yml
				//connects to a topic
				//streamBridge.send("order-outbound", response);
				logger.info("ORDER EXECUTED : COMMAND LINE RUNNER");
				Thread.sleep(1200);
			}
		};
	}

}
