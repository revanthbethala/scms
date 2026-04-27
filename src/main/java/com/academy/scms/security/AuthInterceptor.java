package com.academy.scms.security;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.academy.scms.utils.ErrorResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;

	public AuthInterceptor(JwtUtil jwtUtil, ObjectMapper objectMapper) {
		this.jwtUtil = jwtUtil;
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
		String method = request.getMethod();

		if (path.startsWith("/api/v1/courses") && !path.matches(".*/api/v1/courses/\\d+/students")
				&& method.equals("GET")) {
			return true;
		}

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED,
					"Please login to access this resource");
			sendJsonError(response, error, HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		String token = authHeader.substring(7);
		String subject = jwtUtil.validateTokenAndGetSubject(token);
		if (subject == null) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED,
					"Invalid or expired token");
			sendJsonError(response, error, HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		if (path.startsWith("/api/v1/students/") && List.of("PUT", "POST", "DELETE").contains(method)) {
			String[] pathParts = path.split("/");
			if (pathParts.length >= 5) {
				String idFromPath = pathParts[4];
				if (idFromPath.matches("\\d+")) {
					if (!idFromPath.equals(subject)) {
						Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.FORBIDDEN,
								"Access Denied: You can only modify your own data");
						sendJsonError(response, error, HttpServletResponse.SC_FORBIDDEN);
						return false;
					}
				}
			}
		}
		System.out.println("ID:" + subject);
		request.setAttribute("subject", Integer.valueOf(subject));
		return true;
	}

	private void sendJsonError(HttpServletResponse response, Map<String, Object> error, int status) throws Exception {

		response.setStatus(status);
		response.setContentType("application/json");

		objectMapper.writeValue(response.getWriter(), error);
	}
}