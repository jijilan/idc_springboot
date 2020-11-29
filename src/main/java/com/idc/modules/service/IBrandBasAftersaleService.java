package com.idc.modules.service;

import com.idc.modules.entity.BrandBasAftersale;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.BrandBasCreQua;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书） 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasAftersaleService extends IService<BrandBasAftersale> {
    List<Map> getBrandBasLicense(int brandId);
    Map checkBeanIsNull(BrandBasAftersale brandBasAftersale, List<String> checkList);
}
