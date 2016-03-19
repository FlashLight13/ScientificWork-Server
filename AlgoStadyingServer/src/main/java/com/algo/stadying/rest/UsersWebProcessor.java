package com.algo.stadying.rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algo.stadying.data.entities.User;
import com.algo.stadying.data.entities.User.Type;
import com.algo.stadying.data.repositories.PlayerRepository;

@RestController("/web")
public class UsersWebProcessor {

	@Autowired
	PlayerRepository playerRepository;

	@RequestMapping(value = "/web/get_user")
	public User getUser(@RequestParam("id") String id) {
		return playerRepository.findOne(id);
	}

	@RequestMapping(value = "/web/get_users")
	public List<UserWeb> getUsers() {
		List<UserWeb> result = new ArrayList<UserWeb>();
		for (User user : playerRepository.findAll()) {
			result.add(new UserWeb(user.getLogin(), user.getName()));
		}
		return result;
	}

	@RequestMapping(value = "/web/register")
	public void register(@RequestParam("login") String login, @RequestParam("type") String type,
			@RequestParam("pass") String pass, @RequestParam("name") String name) {
		User result = new User(login, pass);
		result.setName(name);
		result.setType(Type.valueOf(type));
		playerRepository.save(result);
	}

	@RequestMapping(value = "/web/remove")
	public void remove(@RequestParam("id") String login) {
		playerRepository.delete(login);
	}

	@RequestMapping("/web/pages/{url:.*}")
	public String loadPage(HttpServletRequest request) {
		return load(request);
	}

	@RequestMapping("/web/pages/js/controllers/{url:.*}")
	public String loadScript(HttpServletRequest request) {
		return load(request);
	}

	private String load(HttpServletRequest request) {
		BufferedReader reader = null;
		try {
			StringBuilder pageBuilder = new StringBuilder();
			String url = request.getRequestURI();
			reader = new BufferedReader(new FileReader(url.toString().substring(1, url.length())));
			String line = null;
			while ((line = reader.readLine()) != null) {
				pageBuilder.append(line).append("\n");
			}
			return pageBuilder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static final class UserWeb {
		private String login;
		private String name;

		public UserWeb(String login, String name) {
			this.login = login;
			this.name = name;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
