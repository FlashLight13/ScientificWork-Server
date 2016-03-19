package com.algo.stadying.errors;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {
	private static final long serialVersionUID = -3418548843258761699L;
	
	public final HttpStatus status;
	
	public BaseException(HttpStatus status) {
		super();
		this.status = status;
	}
}
