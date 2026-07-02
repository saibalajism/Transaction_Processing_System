package com.transaction.config_transaction_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigTransactionServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigTransactionServerApplication.class, args);
	}

}
