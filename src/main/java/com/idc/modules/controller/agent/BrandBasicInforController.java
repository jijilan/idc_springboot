package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.*;
import com.idc.modules.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 基础信息表(代理商、品牌商) 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasicInfor")
@RequestMapping("/api/brand-basic-infor/agent")
@Validated
public class BrandBasicInforController extends BaseController {
    @Autowired
    private IBrandBasicInforService iBrandBasicInforService;
    @Autowired
    private IBrandPersonService iBrandPersonService;
    @Autowired
    private IBrandSummaryService iBrandSummaryService;
    @Autowired
    private IBrandBasRevPerfService iBrandBasRevPerfService;
    @Autowired
    private IBrandBasCreQuaService iBrandBasCreQuaService;
    @Autowired
    private IBrandBasPatentService iBrandBasPatentService;
    @Autowired
    private IBrandBasAwardedService iBrandBasAwardedService;
    @Autowired
    private IBrandBasAftersaleService iBrandBasAftersaleService;
    @Autowired
    private IBrandBasStorentpService iBrandBasStorentpService;
    @Autowired
    private IBrandBasPerformanceService iBrandBasPerformanceService;
    @Autowired
    private IBrandUserRoleService iBrandUserRoleService;


    //保存品牌制造商、代理商信息
    @PostMapping(value = "/saveBrandBasicInfor")
    public ResultView saveBrandBasicInfor(String jsonParamStr) {
        JSONObject jsonParam = JSONObject.parseObject(jsonParamStr);
        String basinforStr = jsonParam.getString("basinfor");
        String brandPersonsStr = jsonParam.getString("brandPersons");
        String userIdStr = jsonParam.getString("userId");
        if (EmptyUtil.isEmpty(basinforStr) || EmptyUtil.isEmpty(brandPersonsStr) || EmptyUtil.isEmpty(userIdStr)) {
            return ResultView.error("必要信息不能为空!");
        }
        // 1.保存品牌制造商、代理商基础信息
        BrandBasicInfor brandBasicInfor = JSON.parseObject(basinforStr, BrandBasicInfor.class);
        int userId = jsonParam.getInteger("userId");

        // a.验证基础信息必填字段是否为空
        Map checkMap = iBrandBasicInforService.checkBeanIsNull(brandBasicInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 2.人员信息
        List<BrandPerson> brandPersonList = JSON.parseArray(brandPersonsStr, BrandPerson.class);
        // a.验证人员信息必填字段是否为空
        checkMap = iBrandPersonService.checkBeanListIsNull(brandPersonList);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 3.验证成功后进行保存
        Boolean saveBas = iBrandBasicInforService.save(brandBasicInfor);
        if (saveBas) {
            for (int i = 0; i < brandPersonList.size(); i++) {
                brandPersonList.set(i, brandPersonList.get(i).setBrandId(brandBasicInfor.getId()));
            }
            //插入人员信息
            Boolean savePer = iBrandPersonService.saveBatch(brandPersonList);
            if (savePer) {
                // 最后保存用户与品牌绑定信息
                BrandUserRole brandUserRole = new BrandUserRole();
                brandUserRole.setUserId(userId);
                brandUserRole.setBrandId(brandBasicInfor.getId());
                brandUserRole.setCType(brandBasicInfor.getCType());
                iBrandUserRoleService.save(brandUserRole);
                return ResultView.ok(brandBasicInfor.getId());
            }
        }
        return ResultView.error("保存数据失败");
    }

    @PostMapping(value = "/updateBrandBasicInfor")
    public ResultView updateBrandBasicInfor(String jsonParamStr) {
        JSONObject jsonParam = JSONObject.parseObject(jsonParamStr);
        String basinforStr = jsonParam.getString("basinfor");
        String brandPersonsStr = jsonParam.getString("brandPersons");
        String userIdStr = jsonParam.getString("userId");
        if (EmptyUtil.isEmpty(basinforStr) || EmptyUtil.isEmpty(brandPersonsStr) || EmptyUtil.isEmpty(userIdStr)) {
            return ResultView.error("必要信息不能为空!");
        }
        // 1.保存品牌制造商、代理商基础信息
        BrandBasicInfor brandBasicInfor = JSON.parseObject(basinforStr, BrandBasicInfor.class);
        int userId = jsonParam.getInteger("userId");

        // a.验证基础信息必填字段是否为空
        Map checkMap = iBrandBasicInforService.checkBeanIsNull(brandBasicInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 2.人员信息
        List<BrandPerson> brandPersonList = JSON.parseArray(brandPersonsStr, BrandPerson.class);
        // a.验证人员信息必填字段是否为空
        checkMap = iBrandPersonService.checkBeanListIsNull(brandPersonList);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 3.验证成功后进行更新
        // 更新基础信息
        Boolean saveBas = iBrandBasicInforService.updateById(brandBasicInfor);
        if (saveBas) {
            // 更新人员信息
            iBrandPersonService.updateBatchById(brandPersonList);
            return ResultView.ok();
        }
        return ResultView.error("保存数据失败");
    }

    @PostMapping(value = "/getBrandBasicInfor")
    public ResultView getBrandBasicInfor(@NotNull(message = "用户id不能为空") int userId, @NotNull(message = "品牌商类型不能为空") int cType) {
        Map resMap = new HashMap();
        QueryWrapper<BrandUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BrandUserRole::getUserId, userId).eq(BrandUserRole::getCType, cType);
        BrandUserRole brandUserRole = iBrandUserRoleService.getOne(queryWrapper);
        if (EmptyUtil.isEmpty(brandUserRole)) {
            return ResultView.error(ResultEnum.CODE_24);
        }
        // 获取基础信息
        BrandBasicInfor brandBasicInfor = iBrandBasicInforService.getById(brandUserRole.getBrandId());
        QueryWrapper<BrandPerson> perWrapper = new QueryWrapper<>();
        perWrapper.lambda().eq(BrandPerson::getBrandId, brandUserRole.getBrandId());
        List<BrandPerson> brandPersonList = iBrandPersonService.list(perWrapper);
        resMap.put("basinfor", brandBasicInfor);
        resMap.put("brandPersons", brandPersonList);
        return ResultView.ok(resMap);

    }
    @PostMapping(value = "/checkBrandInforIsOk")
    public ResultView checkBrandInforIsOk( HttpServletRequest request) {
        // 获取当前用户自身的品牌id
        int brandIdSelf=getBrandIdByUser(request);
        // 下面的信息全部检查品牌商的信息
        int brandId=getBrandId(request,"1");
        if(brandIdSelf==0){
            return ResultView.error(24,"品牌基础信息为空！");
        }
        // 1.基础信息是否为空
        BrandBasicInfor brandBasicInfor = iBrandBasicInforService.getById(brandIdSelf);
        if(EmptyUtil.isEmpty(brandBasicInfor)){
            return ResultView.error(24,"品牌基础信息为空！");
        }

        if(brandId==0){
            return ResultView.error(24,"品牌商基础信息为空！");
        }
        // 2.简介是否为空
        QueryWrapper<BrandSummary> brandSummaryQueryWrapper = new QueryWrapper<>();
        brandSummaryQueryWrapper.lambda().eq(BrandSummary::getBrandId, brandId);
        BrandSummary brandSummary=iBrandSummaryService.getOne(brandSummaryQueryWrapper);
        if(EmptyUtil.isEmpty(brandSummary)){
            return ResultView.error(24,"品牌基础信息为空！");
        }
        // 3.brand_bas_rev_perf	证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况）
        QueryWrapper<BrandBasRevPerf> brandBasRevPerfQueryWrapper = new QueryWrapper<>();
        brandBasRevPerfQueryWrapper.lambda().eq(BrandBasRevPerf::getBrandId, brandIdSelf);
        BrandBasRevPerf brandBasRevPerf=iBrandBasRevPerfService.getOne(brandBasRevPerfQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasRevPerf.getYingyezz())){
            return ResultView.error(24,"证明材料 1(基本信息证明）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasRevPerf.getYingysrOne())){
            return ResultView.error(24,"证明材料 2（近三年营收情况）为空！");
        }
        // 4.brand_bas_cre_qua	证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率)
        QueryWrapper<BrandBasCreQua> brandBasCreQuaQueryWrapper = new QueryWrapper<>();
        brandBasCreQuaQueryWrapper.lambda().eq(BrandBasCreQua::getBrandId, brandIdSelf);
        BrandBasCreQua brandBasCreQua=iBrandBasCreQuaService.getOne(brandBasCreQuaQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasCreQua) || EmptyUtil.isEmpty(brandBasCreQua.getXyxzcf())){
            return ResultView.error(24,"证明材料 3(企业信用）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasCreQua.getZlhgzm())){
            return ResultView.error(24,"证明材料 4(产品质量）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasRevPerf.getLvyqkOne())){
            return ResultView.error(24,"证明材料 5（履约评价情况）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasCreQua.getJdzjzmj())){
            return ResultView.error(24,"证明材料 6(建筑面积）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasCreQua.getTxrzfyj())){
            return ResultView.error(24,"证明材料 7(管理体系认证）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasCreQua.getSczyl())){
            return ResultView.error(24,"证明材料 8(市场占有率）为空！");
        }
        // 5.brand_bas_patent	证明材料9：拟入库品牌产品相关的专利证书提供专利复印件
        QueryWrapper<BrandBasPatent> brandBasPatentQueryWrapper = new QueryWrapper<>();
        brandBasPatentQueryWrapper.lambda().eq(BrandBasPatent::getBrandId, brandIdSelf);
        BrandBasPatent brandBasPatent=iBrandBasPatentService.getOne(brandBasPatentQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasCreQua.getSczyl())){
            return ResultView.error(24,"证明材料 9(拟入库品牌产品相关的专利证书提供专利复印件）为空！");
        }
        // 6.brand_bas_awarded	证明材料10.拟入库品牌产品获奖情况
        QueryWrapper<BrandBasAwarded> brandBasAwardedQueryWrapper = new QueryWrapper<>();
        brandBasAwardedQueryWrapper.lambda().eq(BrandBasAwarded::getBrandId, brandIdSelf);
        BrandBasAwarded brandBasAwarded=iBrandBasAwardedService.getOne(brandBasAwardedQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasAwarded)){
            return ResultView.error(24,"证明材料 10(拟入库品牌产品获奖情况）为空！");
        }
        // 7.brand_bas_aftersale	证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书）
        QueryWrapper<BrandBasAftersale> brandBasAftersaleQueryWrapper = new QueryWrapper<>();
        brandBasAftersaleQueryWrapper.lambda().eq(BrandBasAftersale::getBrandId, brandIdSelf);
        BrandBasAftersale brandBasAftersale=iBrandBasAftersaleService.getOne(brandBasAftersaleQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasAftersale)|| EmptyUtil.isEmpty(brandBasAftersale.getDlwz())){
            return ResultView.error(24,"证明材料 11(售后服务机构地理位置）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasAftersale.getShxysj())){
            return ResultView.error(24,"证明材料 12(售后响应时间）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasAftersale.getMfwbq())){
            return ResultView.error(24,"证明材料 13(免费维保期）为空！");
        }
        // 8.brand_bas_storentp	证明材料14.品牌入库情况
        QueryWrapper<BrandBasStorentp> brandBasStorentpQueryWrapper = new QueryWrapper<>();
        brandBasStorentpQueryWrapper.lambda().eq(BrandBasStorentp::getBrandId, brandIdSelf);
        BrandBasStorentp brandBasStorentp=iBrandBasStorentpService.getOne(brandBasStorentpQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasStorentp)){
            return ResultView.error(24,"证明材料 14(品牌入库情况）为空！");
        }
        // 9.brand_bas_performance	证明材料15.产品业绩情况
        QueryWrapper<BrandBasPerformance> brandBasPerformanceQueryWrapper = new QueryWrapper<>();
        brandBasPerformanceQueryWrapper.lambda().eq(BrandBasPerformance::getBrandId, brandIdSelf);
        BrandBasPerformance brandBasPerformance=iBrandBasPerformanceService.getOne(brandBasPerformanceQueryWrapper);
        if(EmptyUtil.isEmpty(brandBasPerformance)){
            return ResultView.error(24,"证明材料 15(产品业绩情况）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasAftersale.getShfa())){
            return ResultView.error(24,"证明材料 16(售后方案）为空！");
        }
        if(EmptyUtil.isEmpty(brandBasAftersale.getShxysjCns())){
            return ResultView.error(24,"证明材料 17(申办材料真实性承诺书）为空！");
        }


        return ResultView.ok();
    }


}
