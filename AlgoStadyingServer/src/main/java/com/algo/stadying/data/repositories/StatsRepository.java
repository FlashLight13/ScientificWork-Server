package com.algo.stadying.data.repositories;

import org.springframework.data.repository.*;

import com.algo.stadying.data.entities.Stat;

public interface StatsRepository extends CrudRepository<Stat, Long> {
}
