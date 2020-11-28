package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSONObject;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 品牌 代理商、制造商 简介--相关产品 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/brand-summary-product")
public class BrandSummaryProductController {
    @PostMapping(value = "/save")
    public ResultView save(@RequestBody JSONObject jsonParam, HttpRequest request) {
        String brandBasLicenseStr=jsonParam.getString("brandBasLicense");
        if(EmptyUtil.isEmpty(brandBasLicenseStr)){
            return  ResultView.error("必要信息不能为空!");
        }
        return  ResultView.error("操作失败");
    }
    @PostMapping(value = "/get")
    public ResultView get(@NotNull(message = "品牌id不能为空") int brandId) {
        return  ResultView.error("操作失败");
    }
}
