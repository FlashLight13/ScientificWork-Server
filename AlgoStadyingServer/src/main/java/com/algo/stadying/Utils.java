package com.algo.stadying;

import java.io.Closeable;

public class Utils {

	public static void safeClose(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			}
		}
	}
}
