package com.algo.stadying.data.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.algo.stadying.data.entities.Task;
import com.algo.stadying.data.entities.TaskGroup;
import com.algo.stadying.data.entities.User;
import com.algo.stadying.data.entities.User.Type;
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

	@Transactional
	public TaskGroup updateTaskGroup(TaskGroup newTaskGroup, Task task) {
		TaskGroup existedTaskGroup = taskGroupRepository.findOne(newTaskGroup.getId());
		List<Task> tasks = existedTaskGroup.getTasks();
		tasks.add(task);
		existedTaskGroup.setTasks(tasks);
		existedTaskGroup.setTitle(newTaskGroup.getTitle());
		return existedTaskGroup;
	}

	@Transactional
	public TaskGroup createTaskGroup(TaskGroup newTaskGroup, Task task) {
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		newTaskGroup.setTasks(tasks);
		return taskGroupRepository.save(newTaskGroup);
	}

	@Transactional
	public void updateUsers(List<String> users, TaskGroup taskGroup) {
		for (User existedUser : playerController.findPlayers()) {
			if (users.contains(existedUser.getLogin()) || existedUser.getType() == Type.ADMIN) {
				updateUserTaskGroup(existedUser, taskGroup);
			} else {
				removeTaskGroupFromUser(existedUser, taskGroup);
			}
		}
	}

	private void updateUserTaskGroup(User user, TaskGroup taskGroup) {
		if (!containsTaskGroup(user.getTaskGroups(), taskGroup)) {
			List<TaskGroup> taskGroups = user.getTaskGroups();
			taskGroups.add(taskGroup);
			user.setTaskGroups(taskGroups);
		}
	}

	private void removeTaskGroupFromUser(User user, TaskGroup taskGroup) {
		Iterator<TaskGroup> i = user.getTaskGroups().iterator();
		while (i.hasNext()) {
			if (i.next().getId().equals(taskGroup.getId())) {
				i.remove();
			}
		}
	}

	private boolean containsTaskGroup(List<TaskGroup> taskGroups, TaskGroup taskGroup) {
		for (TaskGroup current : taskGroups) {
			if (current.getId().equals(taskGroup.getId())) {
				return true;
			}
		}
		return false;
	}
}
