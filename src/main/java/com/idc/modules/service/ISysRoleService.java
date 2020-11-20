package com.idc.modules.service;

import com.idc.modules.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.common.result.ResultView;

import java.util.List;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
public interface ISysRoleService extends IService<SysRole> {


    /**
     * 新增角色
     *
     * @param roleName
     * @param roleNote
     * @return
     */
    ResultView addRole(String roleName, String roleNote);

    /**
     * 删除角色
     *
     * @param key
     * @return
     */
    ResultView delRole(String key);

    /**
     * 获取角色的权限菜单列表
     *
     * @param key
     * @return
     */
    ResultView getAuthorityByRole(String key);

    /**
     * 获取管理员的角色列表
     *
     * @param managerId
     * @return
     */
    List<SysRole> getRoleListByManager(String managerId);
}
