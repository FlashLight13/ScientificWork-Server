package com.algo.stadying.errors;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException {
	private static final long serialVersionUID = -2171325656073591867L;

	public enum Type {
		WRONG_PASS, NO_SUCH_USER
	}

	public final Type type;

	public AuthException(Type type) {
		super(HttpStatus.UNAUTHORIZED);
		this.type = type;
	}

	public static AuthException noSuchUser() {
		return new AuthException(Type.NO_SUCH_USER);
	}

	public static AuthException wrongPass() {
		return new AuthException(Type.WRONG_PASS);
	}
}
