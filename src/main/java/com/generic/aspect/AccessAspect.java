package com.generic.aspect;

import com.generic.util.Servlets;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect  
@Component 
public class AccessAspect {
    /**@前置通知
     * 方法开始之前执行一段代码
     * @param point
     */
   // @Before("execution(* com.system.*.action.*.*(..))")
    public void before(JoinPoint point) {
    	System.out.println("BEFORE-->");
        HttpServletRequest request = Servlets.getRequest();
        String id = request.getParameter("id");
        System.out.println(id);
        String methodName = point.getSignature().getName();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        System.out.println(method.getName());
        Object[] args = point.getArgs();
        System.out.println("The method 【" + methodName + "】 begins with " + Arrays.asList(args));
        System.out.println();
    }

    /**@后置最终通知
     * 方法执行之后执行一段代码
     * 无论该方法是否出现异常
     * @param point
     */
    //@After("execution(* com.system.app.action.*.*(..))")
    public void after(JoinPoint point) {
    	System.out.println("AFTER-->");
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        System.out.println("The method 【" + methodName + "】 ends with " + Arrays.asList(args));
        System.out.println();
    }

    /**@后置返回通知
     * 方法正常结束后执行的代码，不包括抛出异常的情况
     * 返回通知是可以访问到方法的返回值的
     * @param point
     * @param result
     */
   // @AfterReturning(value="execution(* com.system.*.action.*.*(..))",returning="result")
    public void afterReturning(JoinPoint point,Object result) {
    	System.out.println("AFTERRETURNING-->");
        String methodName = point.getSignature().getName();
        System.out.println("The method 【" + methodName + "】 return with " + result);
        System.out.println();
    }

    /**@后置异常通知
     * 在方法出现异常时会执行的代码
     * 可以访问到异常对象，可以指定在出现特定异常时在执行通知代码
     * @param point
     * @param ex
     */
    //@AfterThrowing(value="execution(* com.system.*.action.*.*(..))", throwing="ex")
    public void afterThrowing(JoinPoint point, Exception ex) {
    	System.out.println("AFTERTHROWING-->");
        String methodName = point.getSignature().getName();
        System.out.println("The method " + methodName + " occurs exception: " + ex);
        System.out.println();
    }

    /**@环绕通知
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 而且环绕通知必须有返回值，返回值即为目标方法的返回值
     * @param point
     * @return
     * @throws Throwable
     */
    //@Around("execution(* com.system.*.action.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
    	System.out.println("ARROUND-->");
        Object result = null;
        System.out.println("target:" + point.getTarget());
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        System.out.println(method.getName());
        String methodName = point.getSignature().getName();
        //执行目标方法
        try {
            //前置通知
            System.out.println("ARROUND-->The method 【" + methodName + "】 begins with 【" + Arrays.asList(point.getArgs()) +"】");
            result = point.proceed();
        } catch (Throwable e) {
            //后置异常通知【在方法出现异常时会执行的代码】
            System.out.println("ARROUND-->The method 【" + methodName + "】 occurs expection : 【" + e +"】");
            throw new RuntimeException(e);
        }
        //后置返回通知【方法正常结束后执行的代码，不包括抛出异常的情况】
        System.out.println("ARROUND-->The method 【" + methodName + "】 return with 【" + result +"】");
        System.out.println();
        return result;
    }

}