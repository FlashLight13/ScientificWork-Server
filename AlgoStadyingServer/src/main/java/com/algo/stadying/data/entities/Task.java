package com.algo.stadying.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private String title;

	private String[][][] gameField;
	private String description;

	public Task() {
	}

	public Task(String title, String[][][] gameField, String description) {
		this.title = title;
		this.gameField = gameField;
		this.description = description;
	}

	public Task(Task task) {
		this.title = task.title;
		this.gameField = task.gameField;
		this.description = task.description;
	}

	public String[][][] getGameField() {
		return gameField;
	}

	public Task setGameField(String[][][] gameField) {
		this.gameField = gameField;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
