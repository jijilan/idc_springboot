package com.idc.modules.controller.agent;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.annotation.log.SysLog;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.CompanyType;
import com.idc.modules.entity.SysUser;
import com.idc.modules.service.ICompanyTypeService;
import com.idc.modules.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 制造商和代理商表 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-22
 */
@RestController("agentCompanyType")
@RequestMapping("/api/companyType/agent")
@Validated
@Slf4j
public class CompanyTypeController extends BaseController {
    @Autowired
    private ICompanyTypeService iCompanyTypeService;
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 获取品牌制造商和代理商的列表
     * @return
     */
    @PostMapping(value = "/getCompanyList")
    public ResultView getCompanyList() {
        QueryWrapper<CompanyType> queryWrapper=new QueryWrapper<>();
        List<CompanyType> companyTypes=iCompanyTypeService.list(queryWrapper);
        return ResultView.ok("查询成功",companyTypes);
    }
    /**
     * 当前管理员信息
     */
    @GetMapping("/getAgentInfo")
    public ResultView getManagerInfo(HttpServletRequest request) {
        String userId = (String) request.getAttribute(SysConstant.USER_ID);
        SysUser sysUser= (SysUser) redisService.getAuthorizedSubject(userId);
        return ResultView.ok(sysUser);
    }


    /**
     * 检查当前用户是否绑定企业类型
     * @param userId
     * @return
     */
    @PostMapping(value = "/CheckUserCompanyType")
    public ResultView CheckUserCompanyType(@NotBlank(message = "用户id不能为空") String userId) {
        Map resMap=new HashMap<>();
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getId,userId);
        SysUser sysUser=iSysUserService.getOne(queryWrapper);
        if(sysUser.getCType()==0){
            resMap.put("isBind",0);
        }else{
            resMap.put("isBind",1);
        }
        resMap.put("cType",sysUser.getCType());
        return ResultView.ok("查询成功",resMap);
    }

}
