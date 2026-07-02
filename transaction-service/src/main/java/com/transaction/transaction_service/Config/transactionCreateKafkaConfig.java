package com.transaction.transaction_service.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class transactionCreateKafkaConfig {

	// -----------------------------------------------------
	// Success Topic Properties
	// -----------------------------------------------------
	@Value("${success.kafka.topic-name}")
	private String successTopicName;

	@Value("${success.kafka.partitions}")
	private int successPartitions;

	@Value("${success.kafka.replication-factor}")
	private short successReplicationFactor;

	@Bean
	public NewTopic createSuccessTopic() {
		return TopicBuilder.name(successTopicName).partitions(successPartitions).replicas(successReplicationFactor)
				.build();
	}

	// -----------------------------------------------------
	// Failure Topic Properties
	// -----------------------------------------------------
	@Value("${fail.kafka.topic-name}")
	private String failTopicName;

	@Value("${fail.kafka.partitions}")
	private int failPartitions;

	@Value("${fail.kafka.replication-factor}")
	private short failReplicationFactor;

	@Bean
	public NewTopic createFailTopic() {
		return TopicBuilder.name(failTopicName).partitions(failPartitions).replicas(failReplicationFactor).build();
	}

}
