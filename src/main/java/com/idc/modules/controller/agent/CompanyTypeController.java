package com.idc.modules.controller.agent;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.entity.CompanyType;
import com.idc.modules.service.ICompanyTypeService;
import com.idc.modules.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
public class CompanyTypeController {
    @Autowired
    private ICompanyTypeService iCompanyTypeService;

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
}
