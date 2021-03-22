package com.example.quartzDemo.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class QuickConfiguration {


	@Bean
	public RestTemplate getTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public HttpHeaders getHttpHeaders() {
		return new HttpHeaders();
	}

}
