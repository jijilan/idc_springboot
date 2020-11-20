package com.idc.modules.service;

import com.idc.common.result.ResultView;
import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.sub.SysManagerSub;
import com.idc.modules.model.QPage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
public interface ISysManagerService extends IService<SysManager> {

    /**
     * 管理员登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return
     */
    SysManager login(String userAccount, String userPassword);

    /**
     * 修改密码
     *
     * @param managerId
     * @param oldPassWord 原密码
     * @param passWord    新密码
     * @return
     */
    ResultView updatePwd(String managerId, String oldPassWord, String passWord);

    /**
     * 获取管理员的角色列表
     *
     * @param key
     * @return
     */
    List<SysRole> getSysRoleListByManager(String key);

    /**
     * 修改管理员的权限角色
     *
     * @param key
     * @param roleIds
     * @return
     */
    ResultView setRoleByManager(String key, String[] roleIds);

    /**
     * 删除管理员
     *
     * @param key
     * @return
     */
    ResultView delManager(String key);

    /**
     * 获取管理员列表
     *
     * @param qPage
     * @param userName     名字
     * @param userAcount   手机号，用户名
     * @param managerLevel 代理类型
     * @param isFlag       代理商状态
     * @param sysManager   当前登录人信息
     * @return
     */
    ResultView getManagerList(QPage qPage, String userName, String userAcount, String managerLevel, String isFlag, SysManager sysManager);

    /**
     * 代理商操作
     *
     * @param managerId
     * @param state     1:正常 2:禁用
     * @return
     */
    ResultView operationManager(String managerId, int state);

    /**
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 编辑代理商
     * @Date 14:00 2019/2/25
     * @Param [sysManager]
     **/
    ResultView editManager(SysManager sysManager);


    /**
     * 代理商微信登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return
     */
    ResultView wxLogin(String userAccount, String userPassword);

    /**
     * 获取上级代理
     *
     * @param managerId
     * @return
     */
    List<SysManagerSub> getBySubId(String managerId);

    /**
     * 获取所有上级代理
     *
     * @param managerId
     * @return
     */
    List<SysManager> getBySuperIdAll(String managerId);

    /**
     * 获取所有下级代理
     *
     * @param managerId
     * @return
     */
    List<SysManagerSub> getBySubAll(String managerId);


    /**
     * 移动递归list到同级中(获取managerId)
     *
     * @param moveList 移动的list
     * @param newList  新的list
     * @return
     */
    List<String> mobilePeer(List<SysManagerSub> moveList, List<String> newList);

    /**
     * @return com.idc.common.result.ResultView
     * @Author jijl
     * @Description: 获取全部一级代理
     * @Date 11:28 2019/3/6
     * @Param []
     **/
    ResultView getAllOneAgent();

    /**
     * 获取自己下级的二级代理
     * @param sysManager
     * @return
     */
    ResultView getAllTwoAgent(SysManager sysManager,String type);


    /**
     * @Author jijl
     * @Description: 根据当前登录人获取所有下级集合包括自己
     * @Date 10:21 2019/3/14
     * @Param [managerId]
     * @return java.util.List<java.lang.String>
     **/
    List<String> getListBySubAll(SysManager sysManager);
}
