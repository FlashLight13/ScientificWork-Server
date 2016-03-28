package com.algo.stadying.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;

import com.algo.stadying.data.entities.User;

public interface PlayerRepository extends CrudRepository<User, String> {

	@Query("SELECT u FROM User u WHERE type = \'STUDENT\'")
	List<User> findAllStudents();
}
