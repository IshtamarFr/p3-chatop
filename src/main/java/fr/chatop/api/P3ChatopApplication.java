package fr.chatop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class P3ChatopApplication {

	public static void main(String[] args) {
		SpringApplication.run(P3ChatopApplication.class, args);
	}

}
