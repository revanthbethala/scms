package com.academy.scms.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationUtil {

	public static Map<String, String> getValidationErrors(BindingResult result) {

		Map<String, String> errMap = new HashMap<>();

		if (result.hasErrors()) {
			for (FieldError err : result.getFieldErrors()) {

				String field = err.getField();
				String message = err.getDefaultMessage();

				if (!errMap.containsKey(field)) {
					errMap.put(field, message);
				}
			}
		}

		return errMap;
	}
}