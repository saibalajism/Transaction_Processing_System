package com.transaction.notification_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.transaction.notification_service.client.accountClient;

@Configuration
public class accountConfig {
	
	@Autowired
	private LoadBalancedExchangeFilterFunction filterFunction;
	
	@Bean
	public WebClient accountWebClient() {
		return WebClient.builder().baseUrl("http://account-service").filter(filterFunction).build();
	}
	
	@Bean
	public accountClient accountsClient() {
		HttpServiceProxyFactory httpProxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(accountWebClient())).build();
		return httpProxyFactory.createClient(accountClient.class);
	}	
	

}
