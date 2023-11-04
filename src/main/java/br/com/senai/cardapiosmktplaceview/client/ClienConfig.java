package br.com.senai.cardapiosmktplaceview.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClienConfig {
	
	@Autowired
	private RestTemplateBuilder builder;

	@Bean
	public RestTemplate getRestTemplate() {
		
		RestTemplate httpClient = builder				
				.errorHandler(new RestTemplateResponseErrorHandler())				
				.build();
		
		httpClient.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
					
		return httpClient;
		
	}
	
}
