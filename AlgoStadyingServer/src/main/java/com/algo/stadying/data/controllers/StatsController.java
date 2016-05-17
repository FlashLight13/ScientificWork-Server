package com.algo.stadying.data.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.algo.stadying.data.entities.Stat;
import com.algo.stadying.data.repositories.StatsRepository;

@Controller
public class StatsController {

	@Autowired
	private StatsRepository repository;
	
	public Stat save(Stat stat) {
		return repository.save(stat);
	}
}
