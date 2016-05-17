package com.algo.stadying.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algo.stadying.Utils;
import com.algo.stadying.data.controllers.PlayerController;
import com.algo.stadying.data.controllers.StatsController;
import com.algo.stadying.data.entities.Stat;
import com.algo.stadying.data.entities.User;
import com.algo.stadying.errors.AuthException;
import com.algo.stadying.errors.ValidationException;

@RestController
public class UsersProcessor {

	@Autowired
	PlayerController controller;
	@Autowired
	StatsController statsController;

	@RequestMapping(value = "/login")
	synchronized public ResponseEntity<Object> auth(@RequestHeader("Authentication") byte[] authData) {
		try {
			return new ResponseEntity<Object>(controller.login(Utils.parseAuthHeader(authData)), HttpStatus.OK);
		} catch (AuthException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register")
	synchronized public ResponseEntity<Object> register(@RequestHeader("Authentication") byte[] authData,
			@RequestBody Map<String, String> request) {
		try {
			User newUser = Utils.parseAuthHeader(authData);
			newUser.setName(request.get("name"));
			newUser.setType(User.Type.valueOf(request.get("type")));
			return new ResponseEntity<Object>(controller.register(newUser), HttpStatus.OK);
		} catch (ValidationException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/getStudents")
	synchronized public ResponseEntity<Object> getStudents() {
		return new ResponseEntity<Object>(controller.findStudents(), HttpStatus.OK);
	}

	@RequestMapping("/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody Map<String, String> request) {
		User player = controller.findPlayer(request.get("login"));
		if (player != null) {
			player.setName(request.get("name"));
			player.setPass(request.get("pass"));
			controller.save(player);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping("/removeUser")
	synchronized public ResponseEntity<Object> removeUser(@RequestBody Map<String, String> request) {
		controller.remove(request.get("login"));
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping("/update_stats")
	synchronized public ResponseEntity<Stat>  updateStats(@RequestHeader("Authentication") byte[] authData,
			@RequestBody Stat stat) {
		User user = controller.findPlayer(Utils.parseAuthHeader(authData).getLogin());
		stat = statsController.save(stat);
		user.getStats().add(stat);
		controller.save(user);
		return new ResponseEntity<Stat>(stat, HttpStatus.OK);
	}

	@RequestMapping("/load_stats")
	synchronized public ResponseEntity<List<Stat>> loadStats(@RequestBody Map<String, String> request) {
		User user = controller.findPlayer(request.get("login"));
		if (user == null) {
			return new ResponseEntity<List<Stat>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Stat>>(user.getStats(), HttpStatus.OK);
	}
}
