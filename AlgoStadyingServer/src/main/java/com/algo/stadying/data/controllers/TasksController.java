package com.algo.stadying.data.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.algo.stadying.Utils;
import com.algo.stadying.data.entities.Task;
import com.algo.stadying.data.entities.TaskGroup;
import com.algo.stadying.data.repositories.TaskGroupsRepository;
import com.algo.stadying.data.repositories.TaskRepository;
import com.algo.stadying.rest.TasksProcessor.WorldData;

@Controller
public class TasksController {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	TaskGroupsRepository taskGroupRepository;

	public WorldData createUpdateTask(Task task, TaskGroup taskGroup) {
		String[][][] gameField = task.getGameField();
		task.setGameField(null);
		task = taskRepository.save(task);
		saveGameWorld(task.getId(), gameField);
		
		taskGroup = prepareTaskGroupTaSave(taskGroup);
		if (taskGroup.getTasks() == null) {
			taskGroup.setTasks(new ArrayList<Task>());
		}
		taskGroup.getTasks().add(task);
		taskGroup = taskGroupRepository.save(taskGroup);
		return new WorldData(taskGroup, task);
	}

	private TaskGroup prepareTaskGroupTaSave(TaskGroup taskGroup) {
		if (taskGroup.getId() != null && taskGroup.getId() > 0) {
			TaskGroup existedGroup = taskGroupRepository.findOne(taskGroup.getId());
			taskGroup.getTasks().addAll(existedGroup.getTasks());
		}
		return taskGroup;
	}

	public TaskGroup updateTaskGroup(TaskGroup taskGroup) {
		TaskGroup existingGroup = taskGroupRepository.findOne(taskGroup.getId());
		existingGroup.setTitle(taskGroup.getTitle());
		return taskGroupRepository.save(existingGroup);
	}

	private void saveGameWorld(long taskId, String[][][] gameWorld) {
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
