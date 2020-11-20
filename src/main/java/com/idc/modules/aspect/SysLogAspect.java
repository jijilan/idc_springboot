package com.idc.modules.aspect;

import com.idc.modules.service.ISysLogService;
import com.idc.common.annotation.log.SysLog;
import com.idc.common.utils.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther: jijl
 * @Date: Create in 2020/12/18
 * @Description:
 **/
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private ISysLogService iSysLogService;

    @Pointcut("@annotation(sysLog)")
    public void log(SysLog sysLog) {

    }

    @Around("log(sysLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(joinPoint, sysLog, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, SysLog sysLog, long time) throws Throwable {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //保存系统日志
        iSysLogService.saveBySystem(request, sysLog,joinPoint, time);
    }
}
