package com.jason.web.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String key() default "#logKey";

    boolean isIgnore() default false;

}
