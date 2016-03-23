package com.algo.stadying;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.algo.stadying.data.entities.Stats;
import com.algo.stadying.data.entities.User;
import com.algo.stadying.data.entities.User.Type;
import com.algo.stadying.data.repositories.PlayerRepository;

@SpringBootApplication
public class AlgoStadyingServerApplication {

	@Autowired
	PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(AlgoStadyingServerApplication.class, args);
	}

	@PostConstruct
	private void init() {
		User admin = new User("admin", "admin", new ArrayList<Stats>(), "Administrator", Type.ADMIN);
		playerRepository.save(admin);
	}
}
