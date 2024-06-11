package io.github.hebertfsoares.ms_produtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MsProdutosApplication implements CommandLineRunner {

	@Value("${spring.application.name}")
	private String appName;

	private final Environment environment;

	public MsProdutosApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(MsProdutosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Spring Application Name (Value): " + appName);
		System.out.println("Spring Application Name (Environment): " + environment.getProperty("spring.application.name"));
	}
}
