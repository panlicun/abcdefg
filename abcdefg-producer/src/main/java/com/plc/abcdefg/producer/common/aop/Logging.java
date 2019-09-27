package com.plc.abcdefg.producer.common.aop;

import com.plc.abcdefg.producer.common.annotion.BussinessLog;
import com.plc.producer.common.annotion.BussinessLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author 潘哥 63105408@qq.com
 * @Date 2018/4/24 0024 15:12
 */
@Aspect
@Component
public class Logging {
    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     */
    @Pointcut("@annotation(com.plc.producer.common.annotion.BussinessLog)")
    private void cut() {
    }

    /**
     * 前置方法,在目标方法执行前执行
     * @param joinPoint 封装了代理方法信息的对象,若用不到则可以忽略不写
     */
    @Before("cut()")
    public void BeforeCall(JoinPoint joinPoint) {
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        BussinessLog annotation = method.getAnnotation(BussinessLog.class);
        System.out.println("打印：" + annotation.value() + " 前置日志");
        System.out.println("事前通知");
    }


    /**
     * 环绕方法,可自定义目标方法执行的时机
     * @param joinPoint JoinPoint的子接口,添加了
     *            Object proceed() throws Throwable 执行目标方法
     *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
     *            两个方法
     * @return 此方法需要返回值,返回值视为目标方法的返回值
     */
    @Around("cut()")
    public Object AroundCall(ProceedingJoinPoint joinPoint) {
        //先执行业务

        System.out.println("环绕通知之开始");
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("环绕通知之结束");
        return result;
    }

    @After("cut()")
    public void AfterCall() {
        System.out.println("事后通知");
    }
}
