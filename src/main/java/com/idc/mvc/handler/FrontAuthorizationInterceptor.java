package com.idc.mvc.handler;

import com.alibaba.druid.util.StringUtils;
import com.idc.common.enums.ResultEnum;
import com.idc.common.exception.AuthException;
import com.idc.common.redis.RedisService;
import com.idc.common.result.SysConstant;
import com.idc.modules.entity.SysManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther Dyaln
 * @Date 2020/11/22
 */
public class FrontAuthorizationInterceptor  implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;
    /**
     * @Description 权限拦截 接口权限
     * @Date 2020/11/22
     * @Author Dylan
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求资源路径
        String requestURI = request.getRequestURI();
        String token = request.getHeader(SysConstant.TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new AuthException(ResultEnum.CODE_3);
        }
        return true;
    }
}
