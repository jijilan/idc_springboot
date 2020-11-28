package com.idc.modules.service;

import com.idc.modules.entity.BrandPerson;
import com.idc.modules.entity.BrandSummaryApply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌简介-所申报品牌产品应用情况 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-28
 */
public interface IBrandSummaryApplyService extends IService<BrandSummaryApply> {
    Map checkBeanListIsNull(List<BrandSummaryApply> brandSummaryApplies);

}
