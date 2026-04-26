package com.academy.scms.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

	private static final Logger log = LogManager.getLogger(ExecutionTimeAspect.class);

	@Around("execution(* com.academy.scms.service..*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long time = System.currentTimeMillis() - start;

		log.info("{} executed in {} ms", joinPoint.getSignature().toShortString(), time);

		return result;
	}
}