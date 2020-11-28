package com.idc.modules.service;

import com.idc.modules.entity.BrandBasRevPerf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.BrandSummaryApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况） 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasRevPerfService extends IService<BrandBasRevPerf> {
    Map checkBeanListIsNull(List<BrandBasRevPerf> brandBasRevPerfs,List<String> checkList);
    List<Map> getBrandBasLicense(List<Integer> brandIds);
}
