package com.example.commentcomandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.commentcomandservice.repository.CommentRepository")
@SpringBootApplication
public class CommentCommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentCommandServiceApplication.class, args);
	}

}
