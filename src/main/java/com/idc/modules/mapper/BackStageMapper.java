package com.idc.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idc.modules.entity.excle.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface BackStageMapper{
    /*分页查询填报列表*/
    List<BrandInforListExcle> selectBrandPage(IPage ipage,@Param("paraMap")Map<String,Object> map);

    List<BrandInforListExcle> selectBrandPage(@Param("paraMap")Map<String,Object> map);
    int getBrandManuId(@Param("id")int id);
    BrandCountExcle getSummaryNum(@Param("beginDate") String beginDate,@Param("endDate")String endDate);
    List<EnterpriseInforExcle> getEnterpriseInforList(IPage ipage, @Param("beginDate") String beginDate, @Param("endDate")String endDate);
    List<EnterpriseInforExcle> getEnterpriseInforList( @Param("beginDate") String beginDate, @Param("endDate")String endDate);

    List<BrandSummaryInforExcel> getBrandSummaryInforList(IPage ipage, @Param("beginDate") String beginDate, @Param("endDate")String endDate);
    List<BrandSummaryInforExcel> getBrandSummaryInforList(@Param("beginDate") String beginDate, @Param("endDate")String endDate);

    List<BrandProductInforListExcle> getBrandProductInforList(IPage ipage, @Param("beginDate") String beginDate, @Param("endDate")String endDate);
    List<BrandProductInforListExcle> getBrandProductInforList(@Param("beginDate") String beginDate, @Param("endDate")String endDate);

    List<Map> getsysUserList(@Param("paraMap")Map<String,Object> map);
}
