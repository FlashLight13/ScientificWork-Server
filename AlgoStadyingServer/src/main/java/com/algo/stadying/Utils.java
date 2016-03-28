package com.algo.stadying;

import java.io.Closeable;

import com.algo.stadying.data.entities.User;

public class Utils {

	public static final String DIVIDER = "%";

	public static void safeClose(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			}
		}
	}

	public static User parseAuthHeader(byte[] authData) {
		String authString = new String(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(authData));
		String login = authString.substring(0, authString.indexOf(DIVIDER));
		String password = authString.substring(login.length() + 1);
		return new User(login, password, null, null, null);
	}
}
