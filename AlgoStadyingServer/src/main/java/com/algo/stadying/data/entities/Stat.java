package com.algo.stadying.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stat {
	
	@Id
	private long id;
	private String groupName;
	private String taskName;
	private long time;
	private int commandsCount = Integer.MAX_VALUE;
	private long groupId;
	private long taskId;

	public Stat() {

	}

	public String getGroupName() {
		return groupName;
	}

	public Stat setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public Stat setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getCommandsCount() {
		return commandsCount;
	}

	public void setCommandsCount(int commandsCount) {
		this.commandsCount = commandsCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
}
