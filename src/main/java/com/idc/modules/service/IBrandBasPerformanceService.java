package com.idc.modules.service;

import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasPerformance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料15.产品业绩情况 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasPerformanceService extends IService<BrandBasPerformance> {
    Map checkBeanListIsNull(List<BrandBasPerformance> brandBasPerformances);
}
