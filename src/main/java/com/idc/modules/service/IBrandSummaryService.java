package com.idc.modules.service;

import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandSummary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 品牌 制造商、代理商 简介表 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandSummaryService extends IService<BrandSummary> {
    Map checkBeanIsNull(BrandSummary brandSummary);
}
