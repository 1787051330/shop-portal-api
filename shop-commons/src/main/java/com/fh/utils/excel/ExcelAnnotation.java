package com.fh.utils.excel;

import java.lang.annotation.*;

/**
 * @ClassName ExcelAnnotation
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */

@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {

    String value() default "";
    String title() default "";
    String header() default "";
}
