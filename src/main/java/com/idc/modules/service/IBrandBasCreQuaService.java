package com.idc.modules.service;

import com.idc.modules.entity.BrandBasCreQua;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.BrandBasRevPerf;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率) 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasCreQuaService extends IService<BrandBasCreQua> {
    List<Map> getBrandBasLicense(int brandId);
    Map checkBeanIsNull(BrandBasCreQua BrandBasCreQua, List<String> checkList);

}
