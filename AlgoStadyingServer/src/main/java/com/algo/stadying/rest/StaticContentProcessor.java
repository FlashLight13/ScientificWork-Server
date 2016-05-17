package com.algo.stadying.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticContentProcessor {

	private static final String APP_DIR = "/client_app";
	private static final String APP_NAME = "/algo_app.apk";

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/algo_app.apk", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> loadApp(HttpServletResponse response) {
		try {
			final HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			final ClassPathResource apk = new ClassPathResource(APP_DIR + APP_NAME);
			return ResponseEntity.ok().headers(headers).contentLength(apk.contentLength())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(apk.getInputStream()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return (ResponseEntity<InputStreamResource>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
