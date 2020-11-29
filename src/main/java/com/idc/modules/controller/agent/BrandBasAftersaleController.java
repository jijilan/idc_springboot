package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasAftersale;
import com.idc.modules.entity.BrandBasCreQua;
import com.idc.modules.service.IBrandBasAftersaleService;
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
 * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书） 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasAftersale")
@RequestMapping("/api/brand-bas-aftersale/agent")
@Validated
public class BrandBasAftersaleController   extends BaseController {
    @Autowired
    private IBrandBasAftersaleService iBrandBasAftersaleService;

    private int brandId;
    /**
     * 11（售后服务机构地理位置）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandLocation")
    public ResultView saveBrandLocation(@NotNull(message = "售后服务机构地理位置不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasAftersale brandBasAftersale= JSON.parseObject(jsonStr, BrandBasAftersale.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("dlwz");
        checkFields.add("dlwzzzcl");
        Map checkMap= iBrandBasAftersaleService.checkBeanIsNull(brandBasAftersale,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        brandBasAftersale.setBrandId(brandId);
        if(EmptyUtil.isEmpty(brandBasAftersale.getId())){
            // 进行保存操作
            iBrandBasAftersaleService.save(brandBasAftersale);
        }else{
            iBrandBasAftersaleService.updateById(brandBasAftersale);
        }
        return  ResultView.ok("操作成功!");
    }
    /**
     * 12（售后响应时间）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandReplyTime")
    public ResultView saveBrandReplyTime(@NotNull(message = "售后响应时间信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasAftersale brandBasAftersale= JSON.parseObject(jsonStr, BrandBasAftersale.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("shxysj");
        checkFields.add("shxysjCns");
        Map checkMap= iBrandBasAftersaleService.checkBeanIsNull(brandBasAftersale,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasAftersale.setBrandId(brandId);
        UpdateWrapper<BrandBasAftersale> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasAftersale::getShxysj,brandBasAftersale.getShxysj()).set(BrandBasAftersale::getShxysjCns,brandBasAftersale.getShxysjCns())
               .eq(BrandBasAftersale::getBrandId,brandId);
        iBrandBasAftersaleService.update(brandBasAftersale,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 13（免费维保期）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandMaintenance")
    public ResultView saveBrandMaintenance(@NotNull(message = "免费维保期信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasAftersale brandBasAftersale= JSON.parseObject(jsonStr, BrandBasAftersale.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("mfwbq");
        checkFields.add("mfwbqZzcl");
        Map checkMap= iBrandBasAftersaleService.checkBeanIsNull(brandBasAftersale,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasAftersale.setBrandId(brandId);
        UpdateWrapper<BrandBasAftersale> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasAftersale::getMfwbq,brandBasAftersale.getMfwbq()).set(BrandBasAftersale::getMfwbqZzcl,brandBasAftersale.getMfwbqZzcl())
                .eq(BrandBasAftersale::getBrandId,brandId);
        iBrandBasAftersaleService.update(brandBasAftersale,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 16（售后方案）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandProgram")
    public ResultView saveBrandProgram(@NotNull(message = "售后方案信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasAftersale brandBasAftersale= JSON.parseObject(jsonStr, BrandBasAftersale.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("shfa");
        Map checkMap= iBrandBasAftersaleService.checkBeanIsNull(brandBasAftersale,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasAftersale.setBrandId(brandId);
        UpdateWrapper<BrandBasAftersale> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasAftersale::getShfa,brandBasAftersale.getShfa())
                .eq(BrandBasAftersale::getBrandId,brandId);
        iBrandBasAftersaleService.update(brandBasAftersale,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 17（申办材料真实性承诺书）
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandCommitment")
    public ResultView saveBrandCommitment(@NotNull(message = "申办材料真实性承诺书信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        // 品牌商的数据
        BrandBasAftersale brandBasAftersale= JSON.parseObject(jsonStr, BrandBasAftersale.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("sbclzsx");
        Map checkMap= iBrandBasAftersaleService.checkBeanIsNull(brandBasAftersale,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 执行更新方法
        brandBasAftersale.setBrandId(brandId);
        UpdateWrapper<BrandBasAftersale> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(BrandBasAftersale::getSbclzsx,brandBasAftersale.getSbclzsx())
                .eq(BrandBasAftersale::getBrandId,brandId);
        iBrandBasAftersaleService.update(brandBasAftersale,updateWrapper);
        return  ResultView.ok("操作成功!");
    }
    /**
     * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书） 前端控制器
     * @return
     */
    @PostMapping(value = "/getBrandBasInfor")
    public ResultView getBrandBasInfor(HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        if(brandId==0){
            return ResultView.error(24,"品牌制造商基础信息为空！");
        }
        List<Map> brandBasinfor=iBrandBasAftersaleService.getBrandBasLicense(brandId);
        if(EmptyUtil.isEmpty(brandBasinfor)){
            return ResultView.error(24,"信息为空！");
        }
        return  ResultView.ok(brandBasinfor);
    }
}
