package com.academy.scms.utils;

import java.util.Map;
import org.springframework.http.ResponseEntity;

public class SuccessResponseUtil {

	public static ResponseEntity<Object> successResponse(Object data) {
		return ResponseEntity.ok(Map.of("message", "Success", "data", data));
	}
}
