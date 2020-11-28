package com.idc.modules.service;

import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.entity.BrandSummaryProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 代理商、制造商 简介--相关产品 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandSummaryProductService extends IService<BrandSummaryProduct> {
    Map checkBeanListIsNull(List<BrandSummaryProduct> brandSummaryProducts);
}
