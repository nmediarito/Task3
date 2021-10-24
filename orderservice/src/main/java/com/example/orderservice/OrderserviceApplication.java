package com.example.orderservice;

import com.example.orderservice.entities.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class OrderserviceApplication {

	private static final Logger log = LoggerFactory.getLogger(OrderserviceApplication.class);
	private static final String url = "https://random-data-api.com/api/appliance/random_appliance";

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate, StreamBridge streamBridge) throws Exception {
		return args -> {
			try {
				while (!Thread.currentThread().isInterrupted()){
					OrderEvent appliance = restTemplate.getForObject(url, OrderEvent.class);
					assert appliance != null;
					log.info(appliance.toString());
					//The binder name "appliance-outbound" is defined in the application.yml.
					streamBridge.send("OrderEvent-outbound", appliance);
					Thread.sleep(2000);
				}
			}
			catch(InterruptedException ignored){}
		};
	}
}
