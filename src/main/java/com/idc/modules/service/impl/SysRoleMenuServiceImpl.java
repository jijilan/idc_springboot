package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.modules.entity.SysRoleMenu;
import com.idc.modules.mapper.SysRoleMenuMapper;
import com.idc.modules.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {


    @Override
    public List<SysRoleMenu> roleMenuListByRoleIdAndMenuId(String roleId, String menuId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isBlank(roleId)) {
            queryWrapper.eq("roleId", roleId);
        }
        if (!StringUtils.isBlank(menuId)) {
            queryWrapper.eq("menuId", menuId);
        }
        return list(queryWrapper);
    }
}
