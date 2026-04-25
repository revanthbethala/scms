package com.academy.scms.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponseUtil {

	public static Map<String, Object> buildError(HttpStatus status, String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", status.value());
		error.put("error", status.getReasonPhrase());
		error.put("message", message);
		return error;
	}
}