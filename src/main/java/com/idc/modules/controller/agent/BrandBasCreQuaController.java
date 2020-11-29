package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasCreQua;
import com.idc.modules.entity.BrandBasRevPerf;
import com.idc.modules.entity.BrandUserRole;
import com.idc.modules.service.IBrandBasCreQuaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率) 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasCreQua")
@RequestMapping("/api/brand-bas-cre-qua/agent")
@Validated
public class BrandBasCreQuaController  extends BaseController {
    @Autowired
    private IBrandBasCreQuaService iBrandBasCreQuaService;
    private int brandId;

    /**
     * 3.企业信用保存
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandCredit")
    public ResultView saveBrandCredit(@NotNull(message = "品牌代理商企业信用不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasCreQua brandBasCreQua= JSON.parseObject(jsonStr, BrandBasCreQua.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("xyxzcf");
        checkFields.add("xyxzcfImg");
        checkFields.add("xyjyycml");
        checkFields.add("xyjyycmlImg");
        checkFields.add("xyyzsxqymd");
        checkFields.add("xyyzsxqymdImg");
        Map checkMap= iBrandBasCreQuaService.checkBeanIsNull(brandBasCreQua,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        brandBasCreQua.setBrandId(brandId);
        if(EmptyUtil.isEmpty(brandBasCreQua.getId())){
            // 进行保存操作
            iBrandBasCreQuaService.save(brandBasCreQua);
        }else{
            iBrandBasCreQuaService.updateById(brandBasCreQua);
        }
        return  ResultView.ok("操作成功!");
    }

    /**
     * 4.产品质量
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandQuality")
    public ResultView saveBrandQuality(@NotNull(message = "产品质量信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasCreQua brandBasCreQua= JSON.parseObject(jsonStr, BrandBasCreQua.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("zlhgzm");
        checkFields.add("zljcbg");
        checkFields.add("zjzlcns");
        Map checkMap= iBrandBasCreQuaService.checkBeanIsNull(brandBasCreQua,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasCreQua.setBrandId(brandId);
        UpdateWrapper<BrandBasCreQua> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasCreQua::getZlhgzm,brandBasCreQua.getZlhgzm()).set(BrandBasCreQua::getZljcbg,brandBasCreQua.getZljcbg())
                .set(BrandBasCreQua::getZjzlcns,brandBasCreQua.getZjzlcns()).eq(BrandBasCreQua::getBrandId,brandId);
        iBrandBasCreQuaService.update(brandBasCreQua,updateWrapper);
        return  ResultView.ok("操作成功!");
    }

    /**
     * 6（建筑面积）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBuildArea")
    public ResultView saveBrandBuildArea(@NotNull(message = "建筑面积信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasCreQua brandBasCreQua= JSON.parseObject(jsonStr, BrandBasCreQua.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("jdzjzmj");
        checkFields.add("jdzzcl");
        Map checkMap= iBrandBasCreQuaService.checkBeanIsNull(brandBasCreQua,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasCreQua.setBrandId(brandId);
        UpdateWrapper<BrandBasCreQua> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasCreQua::getJdzjzmj,brandBasCreQua.getJdzjzmj()).set(BrandBasCreQua::getJdzzcl,brandBasCreQua.getJdzzcl())
                .eq(BrandBasCreQua::getBrandId,brandId);
        iBrandBasCreQuaService.update(brandBasCreQua,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 7（管理体系认证）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandManageSystem")
    public ResultView saveBrandManageSystem(@NotNull(message = "管理体系认证信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasCreQua brandBasCreQua= JSON.parseObject(jsonStr, BrandBasCreQua.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("txrzfyj");
        checkFields.add("txksyxsj");
        checkFields.add("txyxsc");
        checkFields.add("txzzcl");
        Map checkMap= iBrandBasCreQuaService.checkBeanIsNull(brandBasCreQua,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasCreQua.setBrandId(brandId);
        UpdateWrapper<BrandBasCreQua> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasCreQua::getTxrzfyj,brandBasCreQua.getTxrzfyj()).set(BrandBasCreQua::getTxksyxsj,brandBasCreQua.getTxksyxsj())
                .set(BrandBasCreQua::getTxyxsc,brandBasCreQua.getTxyxsc()).set(BrandBasCreQua::getTxzzcl,brandBasCreQua.getTxzzcl())
                .eq(BrandBasCreQua::getBrandId,brandId);
        iBrandBasCreQuaService.update(brandBasCreQua,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 8(市场占有率)
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandMarketShare")
    public ResultView saveBrandMarketShare(@NotNull(message = "市场占有率信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasCreQua brandBasCreQua= JSON.parseObject(jsonStr, BrandBasCreQua.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("sczyl");
        checkFields.add("sczzcl");
        Map checkMap= iBrandBasCreQuaService.checkBeanIsNull(brandBasCreQua,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasCreQua.setBrandId(brandId);
        UpdateWrapper<BrandBasCreQua> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasCreQua::getSczyl,brandBasCreQua.getSczyl()).set(BrandBasCreQua::getSczzcl,brandBasCreQua.getSczzcl())
                .eq(BrandBasCreQua::getBrandId,brandId);
        iBrandBasCreQuaService.update(brandBasCreQua,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 获取 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率)
     * @return
     */
    @PostMapping(value = "/getBrandBasInfor")
    public ResultView getBrandBasInfor(HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        List<Map> brandBasinfor=iBrandBasCreQuaService.getBrandBasLicense(brandId);
        if(EmptyUtil.isEmpty(brandBasinfor)){
            return ResultView.error(24,"信息为空！");
        }
        return  ResultView.ok(brandBasinfor);
    }
}
