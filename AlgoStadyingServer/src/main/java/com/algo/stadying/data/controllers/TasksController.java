package com.algo.stadying.data.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.algo.stadying.Utils;
import com.algo.stadying.data.entities.Task;
import com.algo.stadying.data.entities.TaskGroup;
import com.algo.stadying.data.repositories.TaskGroupsRepository;
import com.algo.stadying.data.repositories.TaskRepository;
import com.algo.stadying.errors.ValidationException;
import com.algo.stadying.errors.ValidationException.Type;
import com.algo.stadying.rest.TasksProcessor.WorldData;

@Controller
public class TasksController {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	TaskGroupsRepository taskGroupRepository;
	@Autowired
	PlayerController playerController;
	@Autowired
	TaskCreationSubcontroller taskCreationSubcontroller;

	public WorldData createTask(Task task, TaskGroup taskGroup, List<String> users) {
		String[][][] gameField = task.getGameField();
		task.setGameField(null);
		task = taskRepository.save(task);
		taskCreationSubcontroller.saveGameWorld(task.getId(), gameField);

		if (taskGroupRepository.exists(taskGroup.getId())) {
			taskGroup = taskCreationSubcontroller.updateTaskGroup(taskGroup, task);
		} else {
			taskGroup = taskCreationSubcontroller.createTaskGroup(taskGroup, task);
		}
		return new WorldData(taskGroup, task, users);
	}

	private Task updateTasksGameWorld(Task task) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(String.valueOf(task.getId()))));
			task.setGameField((String[][][]) ois.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.safeClose(ois);
		}
		return task;
	}

	public TaskGroup updateTaskGroup(Long id, String newTitle, List<String> availableUsers) throws ValidationException {
		TaskGroup existedGroup = taskGroupRepository.findOne(id);
		if (existedGroup == null) {
			throw new ValidationException(Type.SUCH_TASK_GROUP_NOT_EXISTS);
		}
		existedGroup.setTitle(newTitle);
		taskGroupRepository.save(existedGroup);
		playerController.updateTaskGroup(existedGroup, availableUsers);
		return existedGroup;
	}

	public Task updateTask(Task newTask, Long taskGroupId, List<String> availableUsers) throws ValidationException {
		Task existedTask = getTask(newTask.getId());
		if (existedTask == null) {
			throw new ValidationException(Type.SUCH_TASK_NOT_EXISTS);
		}
		existedTask.setTitle(newTask.getTitle());
		existedTask.setDescription(newTask.getDescription());
		removeTaskFromTaskGroup(existedTask);
		TaskGroup taskGroup = taskGroupRepository.findOne(taskGroupId);
		if (taskGroup == null) {
			throw new ValidationException(Type.SUCH_TASK_GROUP_NOT_EXISTS);
		}
		taskGroup.getTasks().add(existedTask);
		taskRepository.save(existedTask);
		taskGroupRepository.save(taskGroup);
		playerController.updateTaskGroup(taskGroup, availableUsers);
		return existedTask;
	}

	public void removeTaskFromTaskGroup(Task taskToFind) throws ValidationException {
		// TODO rework this method with SQL query
		Iterator<Task> iterator;
		for (TaskGroup group : taskGroupRepository.findAll()) {
			iterator = group.getTasks().iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getId() == taskToFind.getId()) {
					iterator.remove();
					return;
				}
			}
		}
		throw new ValidationException(Type.SUCH_TASK_GROUP_NOT_EXISTS);
	}

	public Task getTask(Long id) {
		Task task = taskRepository.findOne(id);
		return updateTasksGameWorld(task);
	}

	public Iterable<TaskGroup> getTaskGroups() {
		return taskGroupRepository.findAll();
	}

	public void removeTaskGroup(Long id) {
		taskGroupRepository.delete(id);
	}
}
