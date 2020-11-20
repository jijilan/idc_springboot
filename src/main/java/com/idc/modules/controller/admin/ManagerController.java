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
                            @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String userPassword) {
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
     * @param account
     * @param password
     * @param username
     * @return
     */
    @PostMapping("/addManager")
    public ResultView addManager(@NotBlank(message = "账号不能为空") String account, @NotBlank(message = "名称不能为空") String username
            , @NotBlank(message = "密码不能为空") @Length(min = 6, max = 20, message = "密码须在长度6-20位之间") String password) {
        QueryWrapper<SysManager> qw = new QueryWrapper();
        qw.lambda().eq(SysManager::getUserAcount, account);
        if (iSysManagerService.count(qw) > 0) {
            return ResultView.error("账号已存在");
        }
        SysManager manager = new SysManager()
                .setManagerId(IdentityUtil.identityId("MAN"))
                .setUserName(username)
                .setPassWord(DESCode.encode(password))
                .setUserAcount(account)
                .setManagerType(DictionaryEnum.MANAGER_TYPE_GENERAL.getCode())
                .setCtime(new Date())
                .setIsFlag(DictionaryEnum.IS_DEL_Y.getCode());
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
     * @param qKey
     * @return
     */
    @DeleteMapping("/delManager")
    public ResultView delManager(@Valid QSKey qKey) {
        return iSysManagerService.delManager(qKey.getKey());
    }

    /**
     * 修改管理员名称
     *
     * @param managerId
     * @param userName
     * @return
     */
    @PutMapping(value = "/updateManager")
    public ResultView updateManager(@NotBlank(message = "管理员id不能为空") String managerId, @NotEmpty(message = "用户名称不能为空") String userName) {
        SysManager manager = new SysManager().setManagerId(managerId).setUserName(userName);
        return iSysManagerService.updateById(manager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * 获取管理员的角色列表
     *
     * @param qKey 管理员id
     * @return
     */
    @GetMapping("/getRoleListByManager")
    public ResultView getRoleListByManager(@Valid QSKey qKey) {
        return ResultView.ok(iSysManagerService.getSysRoleListByManager(qKey.getKey()));
    }

    /**
     * 设置管理员的权限角色
     *
     * @param qKey    管理员id
     * @param roleIds
     * @return
     */
    @PutMapping("/setRoleByManager")
    public ResultView setRoleByManager(@Valid QSKey qKey, @NotBlank(message = "角色集合不能为空") String... roleIds) {
        return iSysManagerService.setRoleByManager(qKey.getKey(), roleIds);
    }


    /**
     * @param qPage
     * @param userName     名字
     * @param userAcount   手机号，用户名
     * @param managerLevel 代理类型
     * @param isFlag       代理商状态
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 获取管理员列表
     * @Date 16:16 2019/2/22
     **/
    @GetMapping("/getManagerList")
    public ResultView getManagerList(QPage qPage, String userName, String userAcount, String managerLevel, String isFlag, HttpServletRequest request) {
        String managerId = (String) request.getAttribute(SysConstant.MANAGER_ID);
        SysManager sysManager = (SysManager) redisService.getAuthorizedSubject(managerId);
        return iSysManagerService.getManagerList(qPage, userName, userAcount, managerLevel, isFlag, sysManager);
    }


    /**
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 后台新增代理商
     * @Date 16:41 2019/2/22
     * @Param [sysManager]
     **/
    @PostMapping("/addManagerAll")
    public ResultView addManagerAll(SysManager sysManager, HttpServletRequest request) {
        String managerIdis = (String) request.getAttribute(SysConstant.MANAGER_ID);
        SysManager sysManageris = (SysManager) redisService.getAuthorizedSubject(managerIdis);
        QueryWrapper<SysManager> qw = new QueryWrapper();
        qw.lambda().eq(SysManager::getUserAcount, sysManager.getUserAcount());
        if (iSysManagerService.count(qw) > 0) {
            return ResultView.error("账号已存在");
        }
        String managerId = IdentityUtil.identityId(SysConstant.Table.MANAGER_ID_TOP);
        SysManager manager = sysManager
                .setManagerId(managerId)
                .setPassWord(DESCode.encode(sysManager.getPassWord()))
                .setManagerType(DictionaryEnum.MANAGER_TYPE_GENERAL.getCode())
                .setCtime(new Date())
                .setIsFlag(DictionaryEnum.IS_DEL_N.getCode());
        SysManagerRole sysManagerRole = new SysManagerRole();
        //一级代理新增二代理
//        if (sysManageris.getManagerLevel() == DictionaryEnum.MANAGER_LEVLE_2.getCode()) {
//            manager.setSuperId(managerIdis);
//            sysManagerRole.setRoleId(DictionaryEnum.SYS_ROLE_ID_2.getName());
//            manager.setManagerLevel(DictionaryEnum.MANAGER_LEVLE_3.getCode());
//        }else{
//            //总平台新增一级代理
//            if (sysManager.getManagerLevel() == DictionaryEnum.MANAGER_LEVLE_2.getCode() && sysManageris.getManagerType() == DictionaryEnum.MANAGER_TYPE_ADMIN.getCode()) {
//                manager.setSuperId(null);
//                sysManagerRole.setRoleId(DictionaryEnum.SYS_ROLE_ID_1.getName());
//                //总平台新增区域代理
//            } else if (sysManager.getManagerLevel() == DictionaryEnum.MANAGER_LEVLE_3.getCode() && sysManageris.getManagerType() == DictionaryEnum.MANAGER_TYPE_ADMIN.getCode()) {
//                sysManagerRole.setRoleId(DictionaryEnum.SYS_ROLE_ID_2.getName());
//            }
//        }

        sysManagerRole.setManagerId(managerId);
        if (iSysManagerRoleService.save(sysManagerRole)) {
            return iSysManagerService.save(manager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
        } else {
            return ResultView.error(ResultEnum.CODE_2);
        }
    }

    /**
     * 代理商操作
     *
     * @param managerId
     * @param state     1:正常 2:禁用
     * @return
     */
    @PostMapping("/operationManager")
    public ResultView operationManager(@NotBlank(message = "代理商ID不能为空") String managerId, int state) {
        return iSysManagerService.operationManager(managerId, state);
    }

    /**
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 获取密码
     * @Date 11:55 2019/2/25
     * @Param [passWord]
     **/
    @GetMapping("/getPassword")
    public ResultView getPassword(@NotBlank(message = "密码不能为空") String passWord) {
        return ResultView.ok(DESCode.decode(passWord));
    }

    /**
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 编辑代理商
     * @Date 13:41 2019/2/25
     * @Param [sysManager]
     **/
    @PostMapping("/editManager")
    public ResultView editManager(SysManager sysManager) {
        return iSysManagerService.editManager(sysManager);
    }


    /**
     * @return com.idc.common.result.ResultView
     * @Author jijl
     * @Description: 获取全部一级代理
     * @Date 11:27 2019/3/6
     * @Param []
     **/
    @GetMapping("/getAllOneAgent")
    public ResultView getAllOneAgent() {
        return iSysManagerService.getAllOneAgent();
    }

    /**
     * @return com.idc.common.result.ResultView
     * @Author jijl
     * @Description: 获取自己下级的二级代理
     * @Date 11:27 2019/3/6
     * @Param [] type 1:全部  2：自己的
     **/
    @GetMapping("/getAllTwoAgent")
    public ResultView getAllTwoAgent(HttpServletRequest request, String type) {
        String managerIdis = (String) request.getAttribute(SysConstant.MANAGER_ID);
        SysManager sysManager = (SysManager) redisService.getAuthorizedSubject(managerIdis);
        return iSysManagerService.getAllTwoAgent(sysManager, type);
    }


}
