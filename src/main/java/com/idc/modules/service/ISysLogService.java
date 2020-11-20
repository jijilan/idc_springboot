package com.idc.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author jijl
 * @since 2018-12-18
 */
public interface ISysLogService extends IService<SysLog> {



    /**
     * 保存系统日志
     *
     * @param request
     * @param sysLog
     * @param point
     */
    void saveBySystem(HttpServletRequest request, com.idc.common.annotation.log.SysLog sysLog, ProceedingJoinPoint point, long time) throws Throwable;

    /**
     * 保存异常日志
     *
     * @param request
     */
    void saveByError(HttpServletRequest request, Exception e);
}
