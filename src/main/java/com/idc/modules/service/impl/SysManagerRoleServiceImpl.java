package com.idc.modules.service.impl;

import com.idc.modules.entity.SysManagerRole;
import com.idc.modules.mapper.SysManagerRoleMapper;
import com.idc.modules.service.ISysManagerRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员角色表 服务实现类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
@Service
public class  SysManagerRoleServiceImpl extends ServiceImpl<SysManagerRoleMapper, SysManagerRole> implements ISysManagerRoleService {

    @Override
    public int setRoleByManager(String managerId, String[] roleIds) {
        return baseMapper.setRoleByManager(managerId,roleIds);
    }
}
