package com.idc.mvc.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther: jijl
 * @Date: Create in 2020/10/16
 * @Description:
 **/
@Getter
@Setter
@Component
public class InterceptorResource {


    @Value("#{'${interceptor.front-authentication.addPathPatterns}'.split(',')}")
    private String [] frontAuthenticationAddPathPatterns;
    @Value("#{'${interceptor.front-authentication.excludePathPatterns}'.split(',')}")
    private String[] frontAuthenticationExcludePathPatterns;

    @Value("#{'${interceptor.admin-authentication.addPathPatterns}'.split(',')}")
    private String [] adminAuthenticationAddPathPatterns;
    @Value("#{'${interceptor.admin-authentication.excludePathPatterns}'.split(',')}")
    private String[] adminAuthenticationExcludePathPatterns;

    @Value("#{'${interceptor.admin-authorization.addPathPatterns}'.split(',')}")
    private String [] adminAuthorizationAddPathPatterns;
    @Value("#{'${interceptor.admin-authorization.excludePathPatterns}'.split(',')}")
    private String[] adminAuthorizationExcludePathPatterns;
}
