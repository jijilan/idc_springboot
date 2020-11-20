package com.idc.modules.service.impl;

import com.idc.common.redis.RedisService;
import com.idc.common.utils.IdentityUtil;
import com.idc.common.utils.JwtUtil;
import com.idc.modules.entity.SysLog;
import com.idc.modules.entity.SysManager;
import com.idc.modules.mapper.SysLogMapper;
import com.idc.modules.service.ISysLogService;
import com.idc.common.enums.DictionaryEnum;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author jijl
 * @since 2018-12-18
 */
@Slf4j
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private RedisService redisService;

    @Override
    public void saveBySystem(HttpServletRequest request, com.idc.common.annotation.log.SysLog sysLog, ProceedingJoinPoint joinPoint, long time) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String controller = joinPoint.getSignature().getDeclaringTypeName() + "." + methodName;
        SysLog sysLog_ = getSysLog(request);
        sysLog_.setOperation(sysLog.value());
        sysLog_.setTime(time);
        sysLog_.setController(controller);
        StringBuilder sb = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] strings = methodSignature.getParameterNames();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            sb.append(strings[i] + " = " + joinPoint.getArgs()[i] + ";");
        }
        sb.substring(0, sb.length() - 2);
        sysLog_.setParams(sb.toString());
        SysManager sysManager = null;
        //区分是登录日志-还是业务日志
        String login = "login";
        if (methodName.contains(login)) {
            ResultView resultView = (ResultView) joinPoint.proceed();
            String token = (String) resultView.getData();
            String managerId = JwtUtil.getUniqueId(token, SysConstant.MANAGER_ID);
            sysManager = (SysManager) redisService.getAuthorizedSubject(managerId);
        } else {
            sysManager = (SysManager) request.getAttribute(SysConstant.MANAGER);
        }
        sysLog_.setUsername(sysManager.getUserAcount());
        //保存系统日志
        save(sysLog_);
    }

    @Override
    public void saveByError(HttpServletRequest request, Exception e) {
        System.err.println(e);
        String errorMessage = ExceptionUtils.getStackTrace(e);
        SysLog sysLog = getSysLog(request);
        sysLog.setErrorMessage(errorMessage);
        sysLog.setLogType(DictionaryEnum.LOG_ERROR.getCode());
        save(sysLog);
    }

    private SysLog getSysLog(HttpServletRequest request) {
        SysLog sysLog = new SysLog();

        String ip = IdentityUtil.getRemoteHost(request);
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        sysLog.setIp(ip);
        sysLog.setUrl(url);
        sysLog.setMethod(method);
        sysLog.setCtime(new Date());
        return sysLog;
    }
}
