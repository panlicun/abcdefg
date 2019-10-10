package com.plc.abcdefg.kernel.datasource.annotation;

import java.lang.annotation.*;


/**
 * 数据源选择
 * @author owen
 * @create 2017年7月2日
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	//数据库名称
    String name();
}