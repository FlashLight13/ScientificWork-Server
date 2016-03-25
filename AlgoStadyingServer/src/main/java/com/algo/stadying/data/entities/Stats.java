package com.algo.stadying.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String groupName;
	private String taskName;
	private long startTime;
	private int triesCount;

	public Stats() {

	}

	public Stats(String groupName, String taskName, long startTime, int triesCount) {
		this.groupName = groupName;
		this.taskName = taskName;
		this.startTime = startTime;
		this.triesCount = triesCount;
	}

	public String getGroupName() {
		return groupName;
	}

	public Stats setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public Stats setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}

	public long getStartTime() {
		return startTime;
	}

	public Stats setStartTime(long startTime) {
		this.startTime = startTime;
		return this;
	}

	public int getTriesCount() {
		return triesCount;
	}

	public Stats setTriesCount(int triesCount) {
		this.triesCount = triesCount;
		return this;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
