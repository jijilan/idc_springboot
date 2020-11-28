package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.entity.*;
import com.idc.modules.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private IBrandSummaryService iBrandSummaryService;
    @Autowired
    private IBrandSummaryApplyService iBrandSummaryApplyService;
    @Autowired
    private IBrandSummaryProductService iBrandSummaryProductService;

    // 保存品牌基础信息: 基础信息+产品信息
    @PostMapping(value = "/saveBrandSummary")
    public ResultView saveBrandSummary(@RequestBody JSONObject jsonParam) {
        String brandSummaryStr=jsonParam.getString("brandSummary");
        String summaryApplyStr=jsonParam.getString("summaryApplys");
        String summaryProductStr=jsonParam.getString("summaryProducts");
        if(EmptyUtil.isEmpty(brandSummaryStr)||EmptyUtil.isEmpty(summaryApplyStr)||EmptyUtil.isEmpty(summaryProductStr)){
            return  ResultView.error("必要信息不能为空!");
        }
        // 1.简介信息-主表数据
        BrandSummary brandSummary= JSON.parseObject(brandSummaryStr,BrandSummary.class);
        // a.验证基础信息必填字段是否为空
        Map checkMap= iBrandSummaryService.checkBeanIsNull(brandSummary);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 2.简介信息-相关产品信息
        List<BrandSummaryApply> brandSummaryApplies=JSON.parseArray(summaryApplyStr,BrandSummaryApply.class);
        checkMap=iBrandSummaryApplyService.checkBeanListIsNull(brandSummaryApplies);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 3.简介信息-产品应用情况
        List<BrandSummaryProduct> brandSummaryProducts=JSON.parseArray(summaryProductStr,BrandSummaryProduct.class);
        checkMap=iBrandSummaryProductService.checkBeanListIsNull(brandSummaryProducts);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 判断传入的id是否为空
        if(EmptyUtil.isEmpty(brandSummary.getId())){
            // 调用保存接口
            iBrandSummaryService.save(brandSummary);
        }else{
            //调用修改的方法
            iBrandSummaryService.updateById(brandSummary);
            // 后面的列表数据直接删掉之后重新新增
            // 删除应用数据
            UpdateWrapper<BrandSummaryApply> applyUpdateWrapper = new UpdateWrapper();
            applyUpdateWrapper.lambda().eq(BrandSummaryApply::getSumaryId,brandSummary.getId());
            iBrandSummaryApplyService.remove(applyUpdateWrapper);
            // 删除产品数据
            UpdateWrapper<BrandSummaryProduct> productUpdateWrapper = new UpdateWrapper();
            productUpdateWrapper.lambda().eq(BrandSummaryProduct::getSumaryId,brandSummary.getId());
            iBrandSummaryProductService.remove(productUpdateWrapper);
        }
        // 保存其他两个表的数据
        for(int i=0;i<brandSummaryApplies.size();i++){
            brandSummaryApplies.set(i,brandSummaryApplies.get(i).setSumaryId(brandSummary.getId()));
        }
        //插入人员信息
        iBrandSummaryApplyService.saveBatch(brandSummaryApplies);
        for(int i=0;i<brandSummaryProducts.size();i++){
            brandSummaryProducts.set(i,brandSummaryProducts.get(i).setSumaryId(brandSummary.getId()));
        }
        Boolean savePro=iBrandSummaryProductService.saveBatch(brandSummaryProducts);
        if(savePro){
            return ResultView.ok(brandSummary.getId());
        }

        return ResultView.error("保存失败!");
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
    @PostMapping(value = "/getSummaryByBrandId")
    public ResultView getSummaryByBrandId(@NotNull(message = "品牌id不能为空") int brandId) {
        Map resMap=new HashMap();
        // 获取简介基础信息
        BrandSummary brandSummary=iBrandSummaryService.getById(brandId);
        // 获取简介应用列表信息
        QueryWrapper<BrandSummaryApply> applyQueryWrapper=new QueryWrapper<>();
        applyQueryWrapper.lambda().eq(BrandSummaryApply::getSumaryId,brandSummary.getId());
        List<BrandSummaryApply> brandSummaryApplies=iBrandSummaryApplyService.list(applyQueryWrapper);
        // 获取产品列表信息
        QueryWrapper<BrandSummaryProduct> productQueryWrapper=new QueryWrapper<>();
        productQueryWrapper.lambda().eq(BrandSummaryProduct::getSumaryId,brandSummary.getId());
        List<BrandSummaryProduct> brandSummaryProducts=iBrandSummaryProductService.list(productQueryWrapper);
        resMap.put("brandSummary",brandSummary);
        resMap.put("brandSummaryApplies",brandSummaryApplies);
        resMap.put("brandSummaryProducts",brandSummaryProducts);
        if(EmptyUtil.isEmpty(brandSummary)){
            return ResultView.error("简介信息为空!");
        }
        return ResultView.ok(resMap);
    }





}
