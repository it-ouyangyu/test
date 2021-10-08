package com.ict.springmvcfs.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
@Aspect
public class Log {

    @Around("execution(* com.ict.springmvcfs.controller.*.post(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        //打印时间
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        System.out.println("现在时间：" + sdf.format(date));

        //获取参数并打印
        Object[] args= joinPoint.getArgs();
        HttpServletRequest res=(HttpServletRequest)args[0];

        if(res.getParameter("name")!=null){
            System.out.println("输入的用户名为"+res.getParameter("name"));}
        if(res.getParameter("age")!=null){
            System.out.println("输入的用户年龄为"+res.getParameter("age").toString()+"\n");}

        //继续执行原程序
        joinPoint.proceed();

    }

}