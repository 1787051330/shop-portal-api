package com.fh.annotation;

import java.lang.annotation.*;

/**
 * @author xiezhuang
 * @ClassName LoginAnnotation
 * @date 2020/6/29 12:02
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAnnotation {
}
