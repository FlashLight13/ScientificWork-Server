package com.algo.stadying.data.repositories;

import org.springframework.data.repository.*;

import com.algo.stadying.data.entities.User;

public interface StatsRepository extends CrudRepository<User, Long> {

}
