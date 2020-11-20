package com.idc.modules.service;

import com.idc.modules.entity.SysManagerRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员角色表 服务类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
public interface ISysManagerRoleService extends IService<SysManagerRole> {

    /**
     * 设置管理员的权限
     *
     * @param managerId
     * @param roleIds
     * @return
     */
    int setRoleByManager(String managerId, String[] roleIds);
}
