package com.ld.intercity.utils.aop;

import com.ld.intercity.sys.shiro.JWTUtils;
import com.ld.intercity.utils.aop.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author: LD
 * @date:
 * @description:
 * @Before: 前置通知, 在方法执行之前执行
 * @After: 后置通知, 在方法执行之后执行 。
 * @AfterRunning: 返回通知, 在方法返回结果之后执行
 * @AfterThrowing: 异常通知, 在方法抛出异常之后
 * @Around: 环绕通知, 围绕着方法执行
 */
@Slf4j
//切面
@Aspect
//交由ioc容器管理
@Component
public class LogAop {

    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT);
    private static LogModel model = new LogModel();

    //    指定切面哪些类
    @Pointcut("execution(public * com.yts.ytsoa.business.*.*.*.*(..))")
//    @Pointcut("execution(public * com.yts.ytsoa.business.*.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String accId = JWTUtils.getAccId(request);
//            -1 游客
            model.setRequestAccId(accId == null ? "-1" : accId);
            model.setRequestIP(getIPAddress(request));
            model.setRequestURL(request.getRequestURL().toString());
            model.setRequestMethod(request.getMethod());
            model.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            model.setRequestParm(Arrays.toString(joinPoint.getArgs()));
            model.setRequestTime(format.format(System.currentTimeMillis()));
            ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            log.info("REQUEST===>请求标识:{},请求:{}", res, model.toString());
        }
    }

    @AfterReturning(returning = "joinPoint", pointcut = "log()")
    public void doAfterReturning(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        model.setResponeStr(joinPoint.toString());
        log.info("RESPONE===>返回标识:{},返回:{}", res, model.toString());
    }

    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("ERROR===>请求标识:{}", res);
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        log.info("ERROR===>异常代码:" + e.getClass().getName());
        log.info("ERROR===>异常信息:" + e.getMessage());
    }

    //    解析真实ip
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
