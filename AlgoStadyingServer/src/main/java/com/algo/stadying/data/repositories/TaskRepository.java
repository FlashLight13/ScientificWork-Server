package com.algo.stadying.data.repositories;

import org.springframework.data.repository.*;

import com.algo.stadying.data.entities.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{
}
