package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.redis.RedisService;
import com.idc.common.result.SysConstant;
import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysUser;
import com.idc.modules.mapper.SysUserMapper;
import com.idc.modules.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private RedisService redisService;

    @Override
    public SysUser loginByUserName(String userAccount, String userPassword) {
        return baseMapper.loginByUserName(userAccount,userPassword);
    }
}
