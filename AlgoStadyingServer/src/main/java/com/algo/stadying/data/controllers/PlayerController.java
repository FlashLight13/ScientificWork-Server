package com.algo.stadying.data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.algo.stadying.data.entities.TaskGroup;
import com.algo.stadying.data.entities.User;
import com.algo.stadying.data.entities.User.Type;
import com.algo.stadying.data.repositories.PlayerRepository;
import com.algo.stadying.errors.AuthException;
import com.algo.stadying.errors.ValidationException;

@Controller
public class PlayerController {
	@Autowired
	PlayerRepository repository;

	public User login(String login, String pass) throws AuthException {
		User player = repository.findOne(login);
		if (player == null) {
			throw AuthException.noSuchUser();
		}
		if (player.getPass().equals(pass)) {
			return player;
		} else {
			throw AuthException.wrongPass();
		}
	}

	public User register(String login, String password, String name, Type type) throws ValidationException {
		User player = repository.findOne(login);
		if (player != null) {
			throw ValidationException.userAlreadyExists();
		}
		player = new User(login, password);
		player.setType(type);
		player.setName(name);
		repository.save(player);
		return player;
	}

	public void updateTaskGroup(TaskGroup taskGroup, List<String> ids) {
		int taskGroupIndex;
		Iterable<User> users = repository.findAll();
		for (User user : users) {
			taskGroupIndex = containsTaskGroup(user, taskGroup);
			if (ids.contains(user)) {
				if (taskGroupIndex == -1) {
					user.getTaskGroups().add(taskGroup);
				}
			} else {
				if (taskGroupIndex > -1) {
					user.getTaskGroups().remove(taskGroupIndex);
				}
			}
		}
		repository.save(users);
	}

	private int containsTaskGroup(User user, TaskGroup taskGroup) {
		List<TaskGroup> taskGroups = user.getTaskGroups();
		for (int i = 0; i < taskGroups.size(); i++) {
			if (taskGroups.get(i).getId() == taskGroup.getId()) {
				return i;
			}
		}
		return -1;
	}

	public void save(User player) {
		this.repository.save(player);
	}

	public User findPlayer(String login) {
		return repository.findOne(login);
	}

	public void remove(String login) {
		if (login != null) {
			this.repository.delete(login);
		}
	}

	public Iterable<User> findPlayers() {
		return repository.findAll();
	}
}
