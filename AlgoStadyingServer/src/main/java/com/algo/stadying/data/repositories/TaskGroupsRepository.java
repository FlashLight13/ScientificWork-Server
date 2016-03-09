package com.algo.stadying.data.repositories;

import org.springframework.data.repository.*;

import com.algo.stadying.data.entities.TaskGroup;

public interface TaskGroupsRepository extends CrudRepository<TaskGroup, Long> {
}