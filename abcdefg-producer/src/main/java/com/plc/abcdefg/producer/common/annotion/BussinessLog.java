package com.plc.abcdefg.producer.common.annotion;

import java.lang.annotation.*;

/**
 * @Author 潘哥 63105408@qq.com
 * @Date 2018/4/24 0024 15:06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BussinessLog {
    String value() default "";
}
