package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandDictionary;
import com.idc.modules.entity.BrandPerson;
import com.idc.modules.service.IBrandBasicInforService;
import com.idc.modules.service.IBrandDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 制造商、代理商 简介表 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandSummary")
@RequestMapping("/api/brand-summary/agent")
@Validated
public class BrandSummaryController {
    @Autowired
    private IBrandDictionaryService iBrandDictionaryService;
    @Autowired
    private IBrandBasicInforService iBrandBasicInforService;

    // 保存品牌基础信息: 基础信息+产品信息
    @PostMapping(value = "/saveBrandSummary")
    public ResultView saveBrandSummary(@RequestBody JSONObject jsonParam) {
        String brandSummaryStr=jsonParam.getString("basinfor");
        String summaryProductStr=jsonParam.getString("brandPersons");
        String brandIdStr=jsonParam.getString("brandId");
        return ResultView.ok();
    }

    @PostMapping(value = "/getProductListByBrandId")
    public ResultView getProductListByBrandId(@NotNull(message = "品牌id不能为空") int brandId) {
        // 1.获取品牌信息中配置的产品
        QueryWrapper<BrandBasicInfor> basWrapper=new QueryWrapper<>();
        basWrapper.lambda().eq(BrandBasicInfor::getId,brandId);
        BrandBasicInfor basicInfor=iBrandBasicInforService.getOne(basWrapper);
        QueryWrapper<BrandDictionary> proWrapper=new QueryWrapper<>();
        proWrapper.lambda().in(BrandDictionary::getCode,basicInfor.getCailliaolb().split(",")).orderByDesc(BrandDictionary::getBySort);
        List<Map<String,Object>> productInforList= iBrandDictionaryService.listMaps(proWrapper.lambda().select(BrandDictionary::getCode,BrandDictionary::getName,BrandDictionary::getRemark));
        if(EmptyUtil.isEmpty(productInforList)){
            return ResultView.error("产品列表为空");
        }
        return ResultView.ok(productInforList);
    }




}
