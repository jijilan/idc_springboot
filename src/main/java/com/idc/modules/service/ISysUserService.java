package com.idc.modules.service;

import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-21
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 管理员登录
     *
     * @param userName  账号
     * @param passWord 密码
     * @return
     */
    SysUser loginByUserName(String userName, String passWord);

    void updateLastLoginTime(int id);
}
