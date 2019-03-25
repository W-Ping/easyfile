package com.ping.easyfile.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Type;

/**
 * @author liu_wp
 * @date Created in 2019/3/25 11:17
 * @see
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelColumn {
    int index() default 99999;

    String format() default "";
}
