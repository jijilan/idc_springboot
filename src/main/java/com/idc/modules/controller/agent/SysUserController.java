package com.idc.modules.controller.agent;


import com.idc.common.annotation.log.SysLog;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.MD5Util;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysUser;
import com.idc.modules.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.invoke.empty.Empty;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-21
 */
@RestController("agentUser")
@RequestMapping("/api/sys-user/agent")
@Validated
@Slf4j
public class SysUserController  extends BaseController {
    @Autowired
    private ISysUserService iSysUserService;
    @SysLog("用户登录")
    @PostMapping(value = "/login")
    public ResultView login(@NotBlank(message = "用户名不能为空") String userName,
                            @NotBlank(message = "密码不能为空")
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String password) {

        password= MD5Util.endCode(password);
        SysUser sysUser = iSysUserService.loginByUserName(userName,password);
        if (EmptyUtil.isNotEmpty(sysUser)) {
            String token = jwtToken(SysConstant.MANAGER_ID, sysUser.getId()+"", sysUser, SysConstant.ADMIN_AUTH_TIMEOUT);
            return ResultView.ok(token);
        }
        return ResultView.error("账号密码错误");
    }
    @SysLog("用户注册")
    @PostMapping(value = "/regist")
    public ResultView regist(@NotBlank(message = "手机号不能为空") @Length(min = 11, max = 11, message = "手机号长度必须为11位") String phoneNum,
                            @NotBlank(message = "密码不能为空")
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String password) {

//        if (EmptyUtil.isNotEmpty(sysUser)) {
//            String token = jwtToken(SysConstant.MANAGER_ID, sysUser.getId()+"", sysUser, SysConstant.ADMIN_AUTH_TIMEOUT);
//            return ResultView.ok("注册成功",token);
//        }
        return ResultView.error("注册失败");
    }




}
