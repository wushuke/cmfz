package com.baizhi.wsk.aspect;


import com.baizhi.wsk.entity.Admin;
import com.baizhi.wsk.entity.Log;
import com.baizhi.wsk.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;


@Component
@Aspect
@Slf4j
public class TestBeforeAdvice {
    @Autowired
    HttpServletRequest request;


    @Autowired
    LogService logService;

    @Around(value = "pt()")
    public Object testAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        /*
         * 1.环绕通知需要放行
         * 2.环绕的返回值需要返回
         *  日志    什么人   什么时间  做了什么事  事情的执行结果
         * 记录博客系统 管理员的操作行为
         *   1.那些方法做记录  登陆  增撒改
         *   2.数据库
         * */
        Object proceed;
        System.out.println("this is around before");
        //怎么获取执行操作的用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //怎么获取执行时间
        Date date = new Date();
        //怎么获取做了什么事
       // String name = proceedingJoinPoint.getSignature().getName();
        //获取方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象
        com.baizhi.wsk.aspect.Log annotation = method.getAnnotation(com.baizhi.wsk.aspect.Log.class);
        String name = annotation.name();

        //事情的执行结果
        boolean flag = false;

        try {
            proceed = proceedingJoinPoint.proceed();   //调用目标方法  userService.findAll();
            flag = true;
            return proceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            TestBeforeAdvice.log.info("person--->{},date--->{},thing--->{},flag--->{}", admin.getUsername(), date, name, flag);
            Log log = new Log(UUID.randomUUID().toString(),name, admin.getUsername(), date, flag);
            logService.addLog(log);
        }
    }
//    自定义注解  只适用于 代理方式为cglib的情况



    @Pointcut(value = "@annotation(com.baizhi.wsk.aspect.Log)")
    public void pt() {

    }




}
