package com.academy.scms.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final Logger log = LogManager.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(
        pointcut = "execution(* com.academy.scms..*(..))",
        throwing = "ex"
    )
    public void logException(Exception ex) {

        log.error("Exception occurred: ", ex);
    }
}