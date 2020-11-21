package com.idc.modules.controller.agent;


import com.idc.common.annotation.log.SysLog;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
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

    @PostMapping(value = "/login")
    public ResultView login(@NotBlank(message = "用户名不能为空") String userName,
                            @NotBlank(message = "密码不能为空")
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String password) {
        SysUser sysUser = iSysUserService.loginByUserName(userName,password);
        if (EmptyUtil.isNotEmpty(sysUser)) {
            String token = jwtToken(SysConstant.USER_ID, sysUser.getId()+"", sysUser, SysConstant.USER_AUTH_TIMEOUT);
            return ResultView.ok(token);
        }
        return ResultView.error("账号密码错误");
    }

}
