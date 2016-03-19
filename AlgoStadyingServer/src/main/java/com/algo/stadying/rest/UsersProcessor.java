package com.algo.stadying.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algo.stadying.data.controllers.PlayerController;
import com.algo.stadying.data.entities.User;
import com.algo.stadying.errors.AuthException;
import com.algo.stadying.errors.ValidationException;

@RestController
public class UsersProcessor {

	@Autowired
	PlayerController controller;
	public static final String DIVIDER = "%";

	@RequestMapping(value = "/login")
	synchronized public ResponseEntity<Object> auth(@RequestHeader("Authentication") byte[] authData) {
		try {
			String authString = new String(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(authData));
			String login = authString.substring(0, authString.indexOf(DIVIDER));
			String password = authString.substring(login.length() + 1);
			return new ResponseEntity<Object>(controller.login(login, password), HttpStatus.OK);
		} catch (AuthException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register")
	synchronized public ResponseEntity<Object> register(@RequestHeader("Authentication") byte[] authData,
			@RequestBody Map<String, String> request) {
		String authString = new String(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(authData));
		String login = authString.substring(0, authString.indexOf(DIVIDER));
		String password = authString.substring(login.length() + 1);
		try {
			return new ResponseEntity<Object>(
					controller.register(login, password, request.get("name"), User.Type.valueOf(request.get("type"))),
					HttpStatus.OK);
		} catch (ValidationException e) {
			return new ResponseEntity<Object>(e.type.name(), e.status);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/get_users")
	synchronized public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<Object>(controller.findPlayers(), HttpStatus.OK);
	}

	@RequestMapping("/update_user")
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

	@RequestMapping("/remove_user")
	synchronized public ResponseEntity<Object> removeUser(@RequestBody Map<String, String> request) {
		controller.remove(request.get("login"));
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
