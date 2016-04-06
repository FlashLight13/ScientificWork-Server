package com.algo.stadying.data.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "taskgroup")
public class TaskGroup {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	private String title;
	@ManyToMany
	private List<Task> tasks;

	public TaskGroup() {
	}

	public TaskGroup(String title, List<Task> tasks) {
		this.title = title;
		this.tasks = tasks;
	}

	public String getTitle() {
		return title;
	}

	public TaskGroup setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public TaskGroup setTasks(List<Task> tasks) {
		this.tasks = tasks;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
