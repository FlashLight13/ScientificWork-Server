package com.algo.stadying.rest;

import java.util.List;
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
import com.algo.stadying.errors.ValidationException;

@RestController
public class TasksProcessor {

	@Autowired
	TasksController tasksController;

	@RequestMapping(value = "/createOrUpdateWorld")
	public ResponseEntity<WorldData> createOrUpdateWorld(@RequestBody WorldData request) {
		return new ResponseEntity<WorldData>(tasksController.createUpdateTask(request.task, request.taskGroup),
				HttpStatus.OK);
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

	@RequestMapping(value = "/updateTaskGroup")
	public ResponseEntity<Object> updateTaskGroup(@RequestBody UpdateTaskGroupData data) {
		try {
			TaskGroup resultTaskGroup = tasksController.updateTaskGroup(data.id, data.title, data.userIds);
			return new ResponseEntity<>((Object) resultTaskGroup, HttpStatus.OK);
		} catch (ValidationException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		}
	}

	@RequestMapping(value = "/updateTask")
	public ResponseEntity<Object> updateTask(@RequestBody UpdateTaskData data) {
		try {
			Task resultTask = tasksController.updateTask(data.updatedTask, data.taskGroupId, data.usersIds);
			return new ResponseEntity<>((Object) resultTask, HttpStatus.OK);
		} catch (ValidationException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		}
	}

	public static final class UpdateTaskGroupData {
		public String title;
		public Long id;
		public List<String> userIds;

		public UpdateTaskGroupData() {
			super();
		}

		public UpdateTaskGroupData(String title, Long id, List<String> userIds) {
			super();
			this.title = title;
			this.id = id;
			this.userIds = userIds;
		}
	}

	public static final class UpdateTaskData {
		public Task updatedTask;
		public long taskGroupId;
		public List<String> usersIds;

		public UpdateTaskData() {
			super();
		}

		public UpdateTaskData(Task updatedTask, long taskGroupId, List<String> usersIds) {
			super();
			this.updatedTask = updatedTask;
			this.taskGroupId = taskGroupId;
			this.usersIds = usersIds;
		}
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
