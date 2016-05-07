package com.algo.stadying.rest;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class StaticContentProcessor {

	private static final String APP_DIR = "/client_app";
	private static final String APP_NAME = "/algo_app.apk";
	
	@RequestMapping(value = "/loadApp", method = RequestMethod.GET)
	public void loadApp(HttpServletResponse response) {
		try {
			InputStream is = new FileInputStream(APP_DIR + APP_NAME);
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
