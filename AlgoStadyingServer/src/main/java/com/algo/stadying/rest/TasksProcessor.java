package com.algo.stadying.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algo.stadying.data.controllers.TasksController;
import com.algo.stadying.data.entities.Task;
import com.algo.stadying.data.entities.TaskGroup;

@RestController
public class TasksProcessor {

	@Autowired
	TasksController tasksController;

	@RequestMapping(value = "/createOrUpdateWorld")
	public ResponseEntity<WorldData> createOrUpdateWorld(@RequestBody WorldData request) {
		return new ResponseEntity<WorldData>(tasksController.createUpdateTask(request.task, request.taskGroup),
				HttpStatus.OK);
	}

	public ResponseEntity<TaskGroup> updateTaskGroup(@RequestBody TaskGroup taskGroup) {
		return new ResponseEntity<TaskGroup>(tasksController.updateTaskGroup(taskGroup), HttpStatus.OK);
	}

	@RequestMapping(value = "/getTaskGroupsNames")
	public ResponseEntity<Iterable<TaskGroup>> getTaskGroupsNames() {
		return new ResponseEntity<Iterable<TaskGroup>>(tasksController.getTaskGroups(), HttpStatus.OK);
	}

	@RequestMapping(value = "/removeTaskGroup")
	public void removeTaskGroup(@RequestBody Map<String, String> request) {
		tasksController.removeTaskGroup(Long.parseLong(request.get("id")));
	}

	@RequestMapping(value = "/task")
	public ResponseEntity<Object> getTask(@RequestBody Map<String, String> request) {
		return new ResponseEntity<Object>(tasksController.getTask(Long.parseLong(request.get("id"))), HttpStatus.OK);
	}

	public static final class WorldData {
		public TaskGroup taskGroup;
		public Task task;

		public WorldData() {
		}

		public WorldData(TaskGroup taskGroup, Task task) {
			super();
			this.taskGroup = taskGroup;
			this.task = task;
		}
	}
}
