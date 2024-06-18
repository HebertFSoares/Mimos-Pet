package io.github.hebertfsoares.ms_produtos;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class MsProdutosApplication{

	public static void main(String[] args) {
		SpringApplication.run(MsProdutosApplication.class, args);
	}

}
