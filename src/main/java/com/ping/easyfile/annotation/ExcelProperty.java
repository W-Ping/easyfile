package com.ping.easyfile.annotation;

import java.lang.annotation.*;

/**
 * @author liu_wp
 * @date Created in 2019/3/5 10:46
 * @see
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelProperty {

    String[] value() default {""};


    int index() default 99999;


    String format() default "";
}
