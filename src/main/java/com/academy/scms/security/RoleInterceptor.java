package com.academy.scms.security;

import com.academy.scms.enums.UserRole;
import com.academy.scms.utils.ErrorResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class RoleInterceptor implements HandlerInterceptor {

	private final ObjectMapper objectMapper;

	public RoleInterceptor(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		RequireRole annotation = handlerMethod.getMethodAnnotation(RequireRole.class);

		if (annotation == null) {
			return true;
		}
		String userRole = (String) request.getAttribute("role");
		System.out.println("ROLE:"+userRole);
		if (userRole != null
				&& (userRole.equals(annotation.value().name()) || userRole.equals(UserRole.ADMIN.name()))) {
			return true;
		}
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.FORBIDDEN, "Access Denied");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		objectMapper.writeValue(response.getWriter(), error);

		return false;
	}
}
