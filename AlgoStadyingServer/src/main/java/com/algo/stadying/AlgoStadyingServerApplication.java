package com.algo.stadying;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgoStadyingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgoStadyingServerApplication.class, args);
	}

	@PostConstruct
	private void init() {
	}
}
