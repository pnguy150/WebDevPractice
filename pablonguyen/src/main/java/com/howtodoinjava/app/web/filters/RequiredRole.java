package com.howtodoinjava.app.web.filters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RequiredRole {
  public static String USER = "user";
  public static String ADMIN = "admin";

  String role() default USER;
}
