package com.algo.stadying.data.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	public enum Type {
		ADMIN, STUDENT
	}
	
	@Id
	private String login;
	private String pass;

	@ManyToMany
	private List<Stat> stats;
	@ManyToMany
	private List<TaskGroup> taskGroups;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type type;

	public User() {

	}

	public User(String login, String pass, List<Stat> stats, String name, Type type) {
		super();
		this.login = login;
		this.pass = pass;
		this.stats = stats;
		this.name = name;
		this.type = type;
	}

	public User(String login, String pass) {
		super();
		this.login = login;
		this.pass = pass;
	}

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<TaskGroup> getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(List<TaskGroup> taskGroups) {
		this.taskGroups = taskGroups;
	}
}
