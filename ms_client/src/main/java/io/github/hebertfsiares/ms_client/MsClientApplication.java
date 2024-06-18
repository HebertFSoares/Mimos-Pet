package io.github.hebertfsiares.ms_client;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableRabbit
public class MsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClientApplication.class, args);
	}
}
