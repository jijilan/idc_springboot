package com.idc.modules.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.redis.RedisService;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.JwtUtil;
import com.idc.modules.entity.BrandUserRole;
import com.idc.modules.service.IBrandUserRoleService;
import com.idc.mvc.resources.WebResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther: jijl
 * @Date: Create in 2020/10/16
 * @Description:
 **/
public class BaseController {

    @Autowired
    protected RedisService redisService;
    @Autowired
    protected WebResource webResource;
    @Autowired
    protected IBrandUserRoleService iBrandUserRoleService;

    protected int userId;


    /**
     * 生成token
     *
     * @param unique    id标识
     * @param uniqueId  用户编号-或管理员编号
     * @param obj       用户对象或管理员对象
     * @param TTLMillis token有效时间
     * @return
     */
    protected String jwtToken(String unique, String uniqueId, Object obj, long TTLMillis) {
        String jwtToken = JwtUtil.createJWT(unique,
                uniqueId,
                TTLMillis);
        redisService.setAuthorizedSubject(uniqueId, obj, TTLMillis / 1000);
        return jwtToken;
    }

    /**
     * 获取当前用户下的品牌商id
     * @param request
     * @param cType 1 制造商,2 代理商
     * @return
     */
    protected int getBrandId(HttpServletRequest request,String cType){
        int userId=Integer.parseInt(request.getAttribute(SysConstant.USER_ID)+"");
        QueryWrapper<BrandUserRole> brandUserRoleQueryWrapper=new QueryWrapper<>();
        brandUserRoleQueryWrapper.lambda().eq(BrandUserRole::getUserId,userId).eq(BrandUserRole::getCType,cType);
        BrandUserRole brandUserRole=iBrandUserRoleService.getOne(brandUserRoleQueryWrapper);
        if(EmptyUtil.isNotEmpty(brandUserRole)){
            return brandUserRole.getBrandId();
        }
        return 0;
    }

}
