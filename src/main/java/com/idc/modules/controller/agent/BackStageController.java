package com.idc.modules.controller.agent;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idc.common.result.ResultView;
import com.idc.common.utils.DateUtils;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.ExcelUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandPerson;
import com.idc.modules.entity.excle.*;
import com.idc.modules.model.QPage;
import com.idc.modules.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/9
 */
@Slf4j
@RestController("BackStage")
@RequestMapping("/api/backStage/admin")
@Validated
public class BackStageController extends BaseController {
    @Autowired
    private IBackStageService iBackStageService;
    @Autowired
    private IBrandBasicInforService iBrandBasicInforService;

    /**
     * 填报管理-条件查询
     * @param offset 当前页数
     * @param limit 每页条数
     * @param qiylx 申报企业类型
     * @param shenbqy 申报企业名称
     * @param qiyxz 企业性质
     * @param sbzt 申报状态
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandInforListByPage")
    public ResultView getBrandInforListByPage(String qiylx,String shenbqy,String qiyxz,String sbzt,Integer offset,Integer limit,HttpServletRequest request) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("shenbqy",shenbqy);
        parMap.put("qiyxz",qiyxz);
        parMap.put("sbzt",sbzt);
        IPage ipage= iBackStageService.selectBrandPage(qPage,parMap);
        return ResultView.ok(ipage);
    }

    /**
     * 填报管理-导出当前查询的数据
     * @param qiylx 申报企业类型
     * @param shenbqy 申报企业名称y
     * @param qiyxz 企业性质
     * @param sbzt 申报状态
     * @param request
     * @param response
     */
    @PostMapping(value = "/exportBrandInforListByPage")
    public void exportBrandInforListByPage(String qiylx, String shenbqy, String qiyxz, String sbzt, HttpServletRequest request, HttpServletResponse response) {
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("shenbqy",shenbqy);
        parMap.put("qiyxz",qiyxz);
        parMap.put("sbzt",sbzt);
        List<BrandInforListExcle> brandInforListExcles = iBackStageService.selectBrandPage(parMap);
        ExcelUtil.defaultExport(brandInforListExcles,BrandInforListExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }

    /**
     * 根据id获取当前品牌的基础信息、简介、证明材料信息
     * @param id
     * @return
     */
    @PostMapping(value = "/getBrandDetailById")
    public ResultView getBrandDetailById(@NotNull(message = "id不能为空") int id){
        int daiId=0;
        Map resMap=new HashMap();
        //1.承诺书获取当前id的数据
        BrandBasicInfor brandBasicInfor=iBrandBasicInforService.getById(id);
        if(EmptyUtil.isEmpty(brandBasicInfor)){
            return ResultView.error("基础信息为空");
        }
        resMap.put("chengns",brandBasicInfor.getChengns());
        // **若当前id为代理商类型，则获取相应的制造商id进行获取数据**
        if(brandBasicInfor.getCType()==2){
            daiId=iBackStageService.getBrandManuId(id);
        }
        //2.基本信息
        resMap.put("basInfor",iBackStageService.getBasicInfor(id));
        //3.简介基本信息
        resMap.put("sumarryInfor",iBackStageService.getSumarryInfor(id));
        //4.证明材料信息
        resMap.put("materialInfor",iBackStageService.getMaterialInfor(id,daiId));
        return ResultView.ok(resMap);
    }

    /**
     * 统计管理-申报汇总一览表 -查看接口
     * @param request
     * @return
     */
    @PostMapping(value = "/getSummaryNum")
    public ResultView getSummaryNum(HttpServletRequest request,String beginDate,String endDate) {
        BrandCountExcle brandCountExcle=iBackStageService.getSummaryNum(beginDate,endDate);
        return ResultView.ok(brandCountExcle);
    }
    /**
     * 统计管理-申报汇总一览表 -导出接口
     * @param request
     * @return
     */
    @PostMapping(value = "/exportSummaryNum")
    public void exportSummaryNum(HttpServletRequest request, HttpServletResponse response,String beginDate,String endDate) {
        BrandCountExcle brandCountExcle=iBackStageService.getSummaryNum(beginDate,endDate);
        List<BrandCountExcle> brandInforListExcles = new ArrayList<>();
        brandInforListExcles.add(brandCountExcle);
        ExcelUtil.defaultExport(brandInforListExcles,BrandCountExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }

    /**
     *
     * @param request
     * @param offset 当前页数 此处使用integer否则会报错
     * @param limit 每页条数
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @PostMapping(value = "/getEnterpriseInforList")
    public ResultView getEnterpriseInforList(HttpServletRequest request,String beginDate,String endDate,Integer offset,Integer limit) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        IPage<Map> enterpriseInforExcles=iBackStageService.getEnterpriseInforList(qPage,beginDate,endDate);
        return ResultView.ok(enterpriseInforExcles);
    }

    /**
     * 统计管理-申报企业信息一览表
     * @param request
     * @param response
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @PostMapping(value = "/exportEnterpriseInforList")
    public void exportEnterpriseInforList(HttpServletRequest request, HttpServletResponse response,String beginDate,String endDate) {
        List<EnterpriseInforExcle> enterpriseInforExcles=iBackStageService.getEnterpriseInforList(beginDate,endDate);
        ExcelUtil.defaultExport(enterpriseInforExcles,EnterpriseInforExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }

    /**
     * 统计管理-申报品牌信息一览表-列表
     * @param request
     * @param offset 当前页数 此处使用integer否则会报错
     * @param limit 每页条数
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @PostMapping(value = "/getBrandSummaryInforList")
    public ResultView getBrandSummaryInforList(HttpServletRequest request,String beginDate,String endDate,Integer offset,Integer limit) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        IPage<Map> enterpriseInforExcles=iBackStageService.getBrandSummaryInforList(qPage,beginDate,endDate);
        return ResultView.ok(enterpriseInforExcles);
    }

    /**
     * 统计管理-申报品牌信息一览表-列表
     * @param request
     * @param response
     * @param beginDate 开始日期
     * @param endDate 结束日期
     */
    @PostMapping(value = "/exportBrandSummaryInforList")
    public void exportBrandSummaryInforList(HttpServletRequest request, HttpServletResponse response,String beginDate,String endDate) {
        List<BrandSummaryInforExcel> brandSummaryInforExcels=iBackStageService.getBrandSummaryInforList(beginDate,endDate);
        ExcelUtil.defaultExport(brandSummaryInforExcels,BrandSummaryInforExcel.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }
    /**
     * 统计管理-申报品牌产品规格参数一览表-列表
     * @param request
     * @param offset 当前页数 此处使用integer否则会报错
     * @param limit 每页条数
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @PostMapping(value = "/getBrandProductInforList")
    public ResultView getBrandProductInforList(HttpServletRequest request,String beginDate,String endDate,Integer offset,Integer limit) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        IPage<Map> productInforList=iBackStageService.getBrandProductInforList(qPage,beginDate,endDate);
        return ResultView.ok(productInforList);
    }
    /**
     * 统计管理-申报品牌产品规格参数一览表-导出
     * @param request
     * @param response
     * @param beginDate 开始日期
     * @param endDate 结束日期
     */
    @PostMapping(value = "/exportBrandProductInforList")
    public void exportBrandProductInforList(HttpServletRequest request, HttpServletResponse response,String beginDate,String endDate) {
        List<BrandProductInforListExcle> brandProductInforList=iBackStageService.getBrandProductInforList(beginDate,endDate);
        ExcelUtil.defaultExport(brandProductInforList,BrandProductInforListExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }

}
