package com.academy.scms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.academy.scms.security.AuthInterceptor;
import com.academy.scms.security.RoleInterceptor;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableJpaAuditing

public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private AuthInterceptor jwtInterceptor;
	@Autowired
	private RoleInterceptor roleInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/v1/auth/**");
		registry.addInterceptor(roleInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/v1/auth/**");

	}

	@Autowired
	private HttpServletRequest request;

	@Bean
	AuditorAware<Integer> auditorProvider() {
		return () -> {
			Object subject = request.getAttribute("subject");

			if (subject == null) {
				return Optional.empty();
			}
			try {
				return Optional.of(Integer.parseInt(subject.toString()));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		};
	}

}
