package com.transaction.fraud_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.transaction.fraud_service.client.transactionCreateClient;


@Configuration
public class transactionCreateClientConfig {

	@Autowired
	private LoadBalancedExchangeFilterFunction filterFunction;
	
	@Bean
	public WebClient transactionWebClient() {
		return WebClient.builder().baseUrl("http://transaction-service").filter(filterFunction).build();
	}
	
	@Bean
	public transactionCreateClient accountClient() {
		HttpServiceProxyFactory httpProxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(transactionWebClient())).build();
		return httpProxyFactory.createClient(transactionCreateClient.class);
	}

}
