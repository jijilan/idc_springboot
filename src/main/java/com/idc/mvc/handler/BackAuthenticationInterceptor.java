package com.idc.mvc.handler;

import com.alibaba.druid.util.StringUtils;
import com.idc.common.exception.AuthException;
import com.idc.common.redis.RedisService;
import com.idc.common.utils.JwtUtil;
import com.idc.modules.entity.SysManager;
import com.idc.common.enums.DictionaryEnum;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jijl
 * @Description: 后台认证
 * @Date: 2020/7/2 16:50
 **/
@Slf4j
public class BackAuthenticationInterceptor implements HandlerInterceptor {

    static Long startTime = null;
    static Long entTime = null;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthException {
        startTime = System.currentTimeMillis();
        String token = request.getHeader(SysConstant.TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new AuthException(ResultEnum.CODE_3);
        } else {
            try {
                String managerId = JwtUtil.getUniqueId(token, SysConstant.MANAGER_ID);
                /**后台授权拦截业务逻辑部分*/
                SysManager sysManager = (SysManager) redisService.getAuthorizedSubject(managerId);
                if (sysManager.getIsFlag() == DictionaryEnum.ISFLAG_N.getCode()) {
                    throw new AuthException(ResultEnum.CODE_9);
                }
                request.setAttribute(SysConstant.MANAGER, sysManager);
                request.setAttribute(SysConstant.MANAGER_ID, managerId);
            } catch (NullPointerException e) {
                throw new AuthException(ResultEnum.CODE_403);
            } catch (ClassCastException e) {
                throw new AuthException(ResultEnum.CODE_21);
            } catch (AuthException e) {
                throw new AuthException(e.getResultEnum());
            } catch (Exception e) {
                throw new AuthException(ResultEnum.CODE_403);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        entTime = System.currentTimeMillis();
        log.info("总耗时:{}", entTime - startTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
