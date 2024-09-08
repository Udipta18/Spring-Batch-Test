package com.example.batch.springBatch;

import com.example.batch.springBatch.domain.Coffee;
import com.example.batch.springBatch.repos.CoffeeRepo;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class SpringBatchApplication {

	@Autowired
	 CoffeeRepo repo;

	public static void main(String[] args) {


		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
