package com.idc.mvc.handler;

import com.idc.common.enums.ResultEnum;
import com.idc.common.exception.AuthException;
import com.idc.common.redis.RedisService;
import com.idc.common.result.SysConstant;
import com.idc.modules.entity.SysManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @auther: jijl
 * @Date: Create in 2020/8/14
 * @Description: 后台授权
 **/
@Slf4j
public class BackAuthorizationInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisService redisService;

    /**
     * @Description 权限拦截
     * @Date 2020/8/20 21:35
     * @Author liangshihao
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求资源路径
        String requestURI = request.getRequestURI();
        SysManager manager = (SysManager) request.getAttribute(SysConstant.MANAGER);
        //超级管理员直接放行
        if (manager != null && manager.getManagerType() == 1) {
            return true;
        }
        List<String> authorityList = (List<String>) redisService.get(SysConstant.AUTHORITY + manager.getManagerId());
        if (authorityList.contains(requestURI)) {
            return true;
        } else {
            throw new AuthException(ResultEnum.CODE_21);
        }
    }

}
