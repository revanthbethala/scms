package com.academy.scms.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RequestLoggingAspect {

	private static final Logger log = LogManager.getLogger(RequestLoggingAspect.class);

	@Before("execution(* com.academy.scms.controller..*(..))")
	public void logRequest(JoinPoint joinPoint) {

		String className = joinPoint.getSignature().getDeclaringTypeName();
		String method = joinPoint.getSignature().getName();

		log.info("Request: {}.{}", className, method);
		log.debug("Args: {}", Arrays.toString(joinPoint.getArgs()));
	}
}