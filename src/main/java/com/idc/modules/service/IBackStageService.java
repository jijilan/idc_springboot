package com.idc.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.excle.*;
import com.idc.modules.model.QPage;

import java.util.List;
import java.util.Map;

public interface IBackStageService{
    IPage<Map> selectBrandPage(QPage qPage,Map paraMap);
    List<BrandInforListExcle> selectBrandPage(Map paraMap);
    int getBrandManuId(int id);
    Map getSumarryInfor(int id);
    Map getBasicInfor(int id);
    Map getMaterialInfor(int id);
    BrandCountExcle getSummaryNum(String beginDate,String endDate);

    IPage<Map> getEnterpriseInforList(QPage qPage,String beginDate,String endDate);
    List<EnterpriseInforExcle> getEnterpriseInforList(String beginDate,String endDate);

    IPage<Map> getBrandSummaryInforList(QPage qPage,String beginDate,String endDate);
    List<BrandSummaryInforExcel> getBrandSummaryInforList(String beginDate, String endDate);

    IPage<Map> getBrandProductInforList(QPage qPage,String beginDate,String endDate);
    List<BrandProductInforListExcle> getBrandProductInforList(String beginDate, String endDate);
}
