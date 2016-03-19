package com.algo.stadying.errors;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseException {
	private static final long serialVersionUID = 7948492786303575169L;

	public enum Type {
		SUCH_USER_ALREADY_EXISTS, SUCH_TASK_NOT_EXISTS, SUCH_TASK_GROUP_NOT_EXISTS;
	}

	public final Type type;

	public ValidationException(Type type) {
		super(HttpStatus.NOT_ACCEPTABLE);
		this.type = type;
	}

	public static ValidationException userAlreadyExists() {
		return new ValidationException(Type.SUCH_USER_ALREADY_EXISTS);
	}
}
