package com.bsoft.libcommon.commonaop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//让我们的注解保留到哪一步
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logger {

    int value();
}
