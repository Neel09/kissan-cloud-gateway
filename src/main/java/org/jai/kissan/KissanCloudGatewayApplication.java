package org.jai.kissan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class KissanCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KissanCloudGatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		WebClient.Builder builder = WebClient.builder();
		return builder;
	}

}
