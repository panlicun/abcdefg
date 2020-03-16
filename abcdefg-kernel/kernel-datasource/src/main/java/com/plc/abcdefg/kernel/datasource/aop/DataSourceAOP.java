package com.plc.abcdefg.kernel.datasource.aop;

import com.plc.abcdefg.kernel.datasource.annotation.DataSource;
import com.plc.abcdefg.kernel.datasource.constant.DataSourceKey;
import com.plc.abcdefg.kernel.datasource.util.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源Advice
 */
@Aspect
public class DataSourceAOP implements Ordered {
    private Logger log = LoggerFactory.getLogger(DataSourceAOP.class);

    @Pointcut(value = "@annotation(com.plc.abcdefg.kernel.datasource.annotation.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        DataSource datasource = currentMethod.getAnnotation(DataSource.class);
        if (datasource != null) {
            DataSourceHolder.setDataSourceType(datasource.name());
            log.debug("设置数据源为：" + datasource.name());
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DataSourceHolder.clearDataSourceType();
        }
    }


    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }

}