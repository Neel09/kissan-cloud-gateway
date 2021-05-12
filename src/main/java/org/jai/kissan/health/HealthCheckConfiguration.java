package org.jai.kissan.health;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class HealthCheckConfiguration {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Bean
	public ReactiveHealthContributor coreServices() {
		Map<String, ReactiveHealthIndicator> map = new LinkedHashMap<>();
		map.put("farmer-crop-service", () -> getHealth("http://farmer-crop-service"));
		map.put("farmer-fci-service", () -> getHealth("http://farmer-fci-service"));
		map.put("farmer-composite-service", () -> getHealth("http://farmer-composite-service"));
		return CompositeReactiveHealthContributor.fromMap(map);
	}

	private Mono<Health> getHealth(String url) {
		url += "/actuator/health";
		log.debug("Will call the Health API on URL: {}", url);
		return webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String.class)
				.map(s -> new Health.Builder().up().build())
				.onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build())).log();
	}
}
