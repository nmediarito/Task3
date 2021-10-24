package csci318.demo;

import csci318.demo.model.Appliance;
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
public class SpringBootKafkaProducer {

	private static final Logger log = LoggerFactory.getLogger(SpringBootKafkaProducer.class);
	private static final String url = "https://random-data-api.com/api/appliance/random_appliance";

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaProducer.class, args);
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
					Appliance appliance = restTemplate.getForObject(url, Appliance.class);
					assert appliance != null;
					log.info(appliance.toString());
					//The binder name "appliance-outbound" is defined in the application.yml.
					streamBridge.send("appliance-outbound", appliance);
					Thread.sleep(1200);
				}
			}
			catch(InterruptedException ignored){}
		};
	}
}
