package com.idc.modules.mapper;

import com.idc.modules.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-11-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser loginByUserName(@Param("userName") String userName, @Param("passWord") String passWord);

    void updateLastLoginTime(@Param("id") int id);


}
