package com.idc.modules.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idc.common.annotation.log.SysLog;
import com.idc.common.enums.DictionaryEnum;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.DESCode;
import com.idc.common.utils.IdentityUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysManagerRole;
import com.idc.modules.model.QPage;
import com.idc.modules.model.QSKey;
import com.idc.modules.service.ISysManagerRoleService;
import com.idc.modules.service.ISysManagerService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
@RestController("adminManager")
@RequestMapping("/api/sys-manager/admin")
@Validated
@Slf4j
public class ManagerController extends BaseController {

    private final static Logger monitorLogger = LoggerFactory.getLogger("monitor");

    @Autowired
    private ISysManagerService iSysManagerService;
    @Autowired
    private ISysManagerRoleService iSysManagerRoleService;


    /**
     * 当前管理员信息
     */
    @SysLog(value = "获取管理员信息")
    @GetMapping("/getManagerInfo")
    public ResultView getManagerInfo(HttpServletRequest request) {
        String managerId = (String) request.getAttribute(SysConstant.MANAGER_ID);
        SysManager sysManager = (SysManager) redisService.getAuthorizedSubject(managerId);
        return ResultView.ok(sysManager);
    }

    /**
     * 后台登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    @SysLog(value = "登录")
    @PostMapping(value = "/login")
    public ResultView login(@NotBlank(message = "用户名不能为空") String userAccount,
                            @NotBlank(message = "密码不能为空")
                            @Length(min = 4, max = 20, message = "密码须在长度4-20位之间") String userPassword) {
        monitorLogger.info("hello!");
        SysManager manager = iSysManagerService.login(userAccount, userPassword);
        String token = jwtToken(SysConstant.MANAGER_ID, manager.getManagerId(), manager, SysConstant.ADMIN_AUTH_TIMEOUT);
        return ResultView.ok(token);
    }

    /**
     * 修改密码
     *
     * @param passWord
     * @param request
     * @return
     */
    @SysLog(value = "修改密码")
    @PutMapping(value = "/updatePwd")
    public ResultView updatePwd(HttpServletRequest request, @NotBlank(message = "原密码不能为空") String oldPassWord,
                                @NotBlank(message = "新密码不能为空") @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String passWord) {
        String managerId = (String) request.getAttribute(SysConstant.MANAGER_ID);
        return iSysManagerService.updatePwd(managerId, oldPassWord, passWord);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public ResultView logout(HttpServletRequest request) {
        String managerId = (String) request.getAttribute(SysConstant.MANAGER_ID);
        redisService.delAuthorizedSubject(managerId);
        return ResultView.ok();
    }

    /**
     * 新增管理员
     *
     * @param userAcount
     * @param passWord
     * @param userName
     * @return
     */
    @PostMapping("/addManager")
    public ResultView addManager(@NotBlank(message = "账号不能为空") String userAcount, @NotBlank(message = "名称不能为空") String userName
            , @NotBlank(message = "密码不能为空") @Length(min = 4, max = 20, message = "密码须在长度4-20位之间") String passWord) {
        QueryWrapper<SysManager> qw = new QueryWrapper();
        qw.lambda().eq(SysManager::getUserAcount, userAcount);
        if (iSysManagerService.count(qw) > 0) {
            return ResultView.error("账号已存在");
        }
        SysManager manager = new SysManager()
                .setManagerId(IdentityUtil.identityId("MAN"))
                .setUserName(userName)
                .setPassWord(DESCode.encode(passWord))
                .setUserAcount(userAcount)
                .setManagerType(DictionaryEnum.MANAGER_TYPE_GENERAL.getCode())
                .setCtime(new Date())
                .setIsFlag(DictionaryEnum.IS_DEL_N.getCode());
        return iSysManagerService.save(manager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * 获取管理员列表
     *
     * @return
     */
    @GetMapping("/getManagetList")
    public ResultView getManagetList(QPage qPage) {
        IPage<SysManager> iPage = iSysManagerService.page(new Page(qPage.getOffset(), qPage.getLimit()), null);
        return ResultView.ok(iPage);
    }

    /**
     * 删除管理员
     *
     * @param managerId
     * @return
     */
    @DeleteMapping("/delManager")
    public ResultView delManager(@NotBlank(message = "代理商ID不能为空") String managerId) {
        return iSysManagerService.delManager(managerId);
    }



    /**
     * 获取管理员的角色列表
     *
     * @param managerId 管理员id
     * @return
     */
    @GetMapping("/getRoleListByManager")
    public ResultView getRoleListByManager(@NotBlank(message = "管理员id不能为空") String managerId) {
        return ResultView.ok(iSysManagerService.getSysRoleListByManager(managerId));
    }

    /**
     * 设置管理员的权限角色
     *
     * @param managerId    管理员id
     * @param roleIds
     * @return
     */
    @PostMapping("/setRoleByManager")
    public ResultView setRoleByManager(@NotBlank(message = "代理商ID不能为空") String managerId, @NotBlank(message = "角色集合不能为空") String roleIds) {
        return iSysManagerService.setRoleByManager(managerId, roleIds.split(","));
    }







}
