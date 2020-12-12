package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idc.common.result.ResultData;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.entity.*;
import com.idc.modules.entity.excle.BrandCountExcle;
import com.idc.modules.entity.excle.BrandInforListExcle;
import com.idc.modules.entity.excle.EnterpriseInforExcle;
import com.idc.modules.mapper.BackStageMapper;
import com.idc.modules.model.QPage;
import com.idc.modules.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/9
 */
@Service
@Slf4j
public class BackStageServiceImpl implements IBackStageService {
    @Autowired
    private BackStageMapper backStageMapper;
    @Autowired
    private IBrandBasicInforService iBrandBasicInforService;
    @Autowired
    private IBrandPersonService iBrandPersonService;
    @Autowired
    private IBrandSummaryService iBrandSummaryService;
    @Autowired
    private IBrandSummaryApplyService iBrandSummaryApplyService;
    @Autowired
    private IBrandSummaryProductService iBrandSummaryProductService;
    @Autowired
    private IBrandDictionaryService iBrandDictionaryService;
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
    @Override
    public IPage<Map> selectBrandPage(QPage qPage, Map paraMap) {
        IPage ipage = new Page(qPage.getOffset(), qPage.getLimit());
        ipage.setRecords(backStageMapper.selectBrandPage(ipage, paraMap));
        return ipage;
    }

    @Override
    public List<BrandInforListExcle> selectBrandPage(Map paraMap) {
        return backStageMapper.selectBrandPage(paraMap);
    }

    @Override
    public int getBrandManuId(int id) {
        return backStageMapper.getBrandManuId(id);
    }
    /**
     * 获取品牌简介信息
     * @param id
     * @return
     */
    @Override
    public Map getSumarryInfor(int id) {
        Map resMap=new HashMap();
        // 获取简介基础信息
        QueryWrapper<BrandSummary> brandSummaryQueryWrapper=new QueryWrapper<>();
        brandSummaryQueryWrapper.lambda().eq(BrandSummary::getBrandId,id);
        BrandSummary brandSummary=iBrandSummaryService.getOne(brandSummaryQueryWrapper);
        List<BrandSummaryApply> brandSummaryApplies=new ArrayList<>();
        List<BrandSummaryProduct> brandSummaryProducts=new ArrayList<>();
        if(EmptyUtil.isNotEmpty(brandSummary)){
            log.info("基础信息不为空，才进行查询");
            // 获取简介应用列表信息
            QueryWrapper<BrandSummaryApply> applyQueryWrapper=new QueryWrapper<>();
            applyQueryWrapper.lambda().eq(BrandSummaryApply::getSumaryId,brandSummary.getId());
            brandSummaryApplies=iBrandSummaryApplyService.list(applyQueryWrapper);
            // 获取产品列表信息
            QueryWrapper<BrandSummaryProduct> productQueryWrapper=new QueryWrapper<>();
            productQueryWrapper.lambda().eq(BrandSummaryProduct::getSumaryId,brandSummary.getId());
            brandSummaryProducts=iBrandSummaryProductService.list(productQueryWrapper);
        }else{
            return null;
        }

        resMap.put("brandSummary",brandSummary);
        resMap.put("brandSummaryApplies",brandSummaryApplies);
        // 1.根据基础id获取产品字典信息
        // 1.获取品牌信息中配置的产品
        QueryWrapper<BrandBasicInfor> basWrapper=new QueryWrapper<>();
        basWrapper.lambda().eq(BrandBasicInfor::getId,id);
        BrandBasicInfor basicInfor=iBrandBasicInforService.getOne(basWrapper);
        QueryWrapper<BrandDictionary> proWrapper=new QueryWrapper<>();
        proWrapper.lambda().in(BrandDictionary::getCode,basicInfor.getCailliaolb().split(",")).orderByDesc(BrandDictionary::getBySort);
        List<Map<String,Object>> productInforList= iBrandDictionaryService.listMaps(proWrapper.lambda().select(BrandDictionary::getCode,BrandDictionary::getName,BrandDictionary::getRemark));
        for(int i=0;i<productInforList.size();i++){
            Map thatParentMap=productInforList.get(i);
            List<BrandSummaryProduct> childProduct=new ArrayList<>();
            for(int j=0;j<brandSummaryProducts.size();j++){
                BrandSummaryProduct thatBrandProduct=brandSummaryProducts.get(j);
                if((thatParentMap.get("code")+"").equals(thatBrandProduct.getDicCode())){
                    childProduct.add(thatBrandProduct);
                }
            }
            thatParentMap.put("childProduct",childProduct);
            productInforList.set(i,thatParentMap);
        }
        resMap.put("brandSummaryProducts",productInforList);
        resMap.put("memo","简介信息");
        return resMap;
    }

    /**
     * 获取品牌基础信息
     * @param id
     * @return
     */
    @Override
    public Map getBasicInfor(int id) {
        Map resMap=new HashMap();
        BrandBasicInfor brandBasicInfor = iBrandBasicInforService.getById(id);
        QueryWrapper<BrandPerson> perWrapper = new QueryWrapper<>();
        perWrapper.lambda().eq(BrandPerson::getBrandId, id);
        List<BrandPerson> brandPersonList = iBrandPersonService.list(perWrapper);
        resMap.put("memo","基础信息");
        resMap.put("basinfor", brandBasicInfor);
        resMap.put("brandPersons", brandPersonList);
        return resMap;
    }
    /**
     * 获取品牌证明材料信息
     * @param id
     * @return
     */
    @Override
    public Map getMaterialInfor(int id) {
        Map resMap=new HashMap();
        // 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况）
        List<Integer> brandIds=new ArrayList<>();
        brandIds.add(id);
        List<Map> basRevPerfMaps=iBrandBasRevPerfService.getBrandBasLicense(brandIds);
        resMap.put("basRevPerf",ResultData.ok("证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况）",basRevPerfMaps.size()!=0?basRevPerfMaps.get(0):null));
        // 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率)
        List<Map> basCreQuaMaps=iBrandBasCreQuaService.getBrandBasLicense(id);
        resMap.put("basCreQua",ResultData.ok("证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率)",basCreQuaMaps.size()!=0?basCreQuaMaps.get(0):null));
        // 证明材料9拟入库品牌产品相关的专利证书提供专利复印件
        QueryWrapper<BrandBasPatent> brandBasPatentQueryWrapper=new QueryWrapper<>();
        brandBasPatentQueryWrapper.lambda().eq(BrandBasPatent::getBrandId,id);
        List<BrandBasPatent> brandBasPatents=iBrandBasPatentService.list(brandBasPatentQueryWrapper);
        resMap.put("brandBasPatents",ResultData.ok("证明材料9（拟入库品牌产品相关的专利证书提供专利复印件）",brandBasPatents));
        // 证明材料10拟入库品牌产品获奖情况
        QueryWrapper<BrandBasAwarded> brandBasAwardedQueryWrapper=new QueryWrapper<>();
        brandBasAwardedQueryWrapper.lambda().eq(BrandBasAwarded::getBrandId,id);
        List<BrandBasAwarded> brandBasAwardeds=iBrandBasAwardedService.list(brandBasAwardedQueryWrapper);
        resMap.put("brandBasAwardeds",ResultData.ok("证明材料10（拟入库品牌产品获奖情况）",brandBasAwardeds));
        // 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）
        List<Map> brandAftersales=iBrandBasAftersaleService.getBrandBasLicense(id);
        resMap.put("brandAftersale",ResultData.ok("证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）",brandAftersales.size()!=0?brandAftersales.get(0):null));
        // 证明材料14品牌入库情况
        QueryWrapper<BrandBasStorentp> brandBasStorentpQueryWrapper=new QueryWrapper<>();
        brandBasStorentpQueryWrapper.lambda().eq(BrandBasStorentp::getBrandId,id);
        List<BrandBasStorentp> brandBasStorentps=iBrandBasStorentpService.list(brandBasStorentpQueryWrapper);
        resMap.put("brandBasStorentps",ResultData.ok("证明材料14（品牌入库情况 ）",brandBasStorentps));
        // 证明材料15产品业绩情况
        QueryWrapper<BrandBasPerformance> brandBasPerformanceQueryWrapper=new QueryWrapper<>();
        brandBasPerformanceQueryWrapper.lambda().eq(BrandBasPerformance::getBrandId,id);
        List<BrandBasPerformance> brandBasPerformances=iBrandBasPerformanceService.list(brandBasPerformanceQueryWrapper);
        resMap.put("brandBasPerformances",ResultData.ok("证明材料15（产品业绩情况 ）",brandBasPerformances));
        return resMap;
    }

    /**
     * 统计管理-申报汇总一览表
     * @return
     */
    @Override
    public BrandCountExcle getSummaryNum(String beginDate,String endDate) {
        return backStageMapper.getSummaryNum(beginDate,endDate);
    }

    @Override
    public IPage<Map> getEnterpriseInforList(QPage qPage, String beginDate, String endDate) {
        IPage ipage = new Page(qPage.getOffset(), qPage.getLimit());
        ipage.setRecords(backStageMapper.getEnterpriseInforList(ipage, beginDate,endDate));
        return ipage;
    }

    @Override
    public List<EnterpriseInforExcle> getEnterpriseInforList(String beginDate, String endDate) {
        return backStageMapper.getEnterpriseInforList(beginDate,endDate);
    }
}
