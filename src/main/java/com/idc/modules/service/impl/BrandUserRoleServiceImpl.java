package com.idc.modules.service.impl;

import com.idc.modules.entity.BrandUserRole;
import com.idc.modules.mapper.BrandUserRoleMapper;
import com.idc.modules.service.IBrandUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌用户权限表。（各用户下面绑定的品牌和代理商信息） 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandUserRoleServiceImpl extends ServiceImpl<BrandUserRoleMapper, BrandUserRole> implements IBrandUserRoleService {

}
