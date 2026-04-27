package com.academy.scms.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.academy.scms.enums.UserRole;

@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface RequireRole {
    UserRole value(); 
}
