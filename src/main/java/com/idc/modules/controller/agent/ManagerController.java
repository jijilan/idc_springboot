package com.idc.modules.controller.agent;



import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.SysManager;
import com.idc.modules.service.ISysManagerService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
@RestController("agentManager")
@RequestMapping("/api/sys-manager/agent")
@Validated
@Slf4j
public class ManagerController extends BaseController {

    @Autowired
    private ISysManagerService iSysManagerService;


    /**
     * 当前代理商信息
     */
    @GetMapping("/get")
    public ResultView getManagerInfo(HttpServletRequest request) {
        String managerId = (String) request.getAttribute(SysConstant.MANAGER_ID);
        SysManager sysManager = (SysManager) redisService.getAuthorizedSubject(managerId);
        return ResultView.ok(sysManager);
    }

    /**
     * 代理商微信登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    @PostMapping(value = "/login")
    public ResultView login(@NotBlank(message = "用户名不能为空") String userAccount,
                            @NotBlank(message = "密码不能为空")
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String userPassword) {
        ResultView resultView = iSysManagerService.wxLogin(userAccount, userPassword);
        if (resultView.getData() != null) {
            SysManager manager = (SysManager) resultView.getData();
            String token = jwtToken(SysConstant.MANAGER_ID, manager.getManagerId(), manager, SysConstant.ADMIN_AUTH_TIMEOUT);
            return ResultView.ok(token);
        }
        return resultView;
    }
}
