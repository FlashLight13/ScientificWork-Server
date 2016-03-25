package com.algo.stadying.data.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.algo.stadying.Utils;
import com.algo.stadying.data.entities.Task;
import com.algo.stadying.data.entities.TaskGroup;
import com.algo.stadying.data.repositories.TaskGroupsRepository;
import com.algo.stadying.data.repositories.TaskRepository;

@Controller
@Transactional
public class TaskCreationSubcontroller {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	TaskGroupsRepository taskGroupRepository;
	@Autowired
	PlayerController playerController;
	
	public TaskGroup updateTaskGroup(TaskGroup newTaskGroup, Task task) {
		TaskGroup existedTaskGroup = taskGroupRepository.findOne(newTaskGroup.getId());
		List<Task> tasks = existedTaskGroup.getTasks();
		tasks.add(task);
		existedTaskGroup.setTasks(tasks);
		existedTaskGroup.setTitle(newTaskGroup.getTitle());
		return existedTaskGroup;
	}

	public TaskGroup createTaskGroup(TaskGroup newTaskGroup, Task task) {
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		newTaskGroup.setTasks(tasks);
		return taskGroupRepository.save(newTaskGroup);
	}
	
	public void saveGameWorld(long taskId, String[][][] gameWorld) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(String.valueOf(taskId))));
			oos.writeObject(gameWorld);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.safeClose(oos);
		}
	}
}
