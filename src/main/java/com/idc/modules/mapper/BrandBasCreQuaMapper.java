package com.idc.modules.mapper;

import com.idc.modules.entity.BrandBasCreQua;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率) Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface BrandBasCreQuaMapper extends BaseMapper<BrandBasCreQua> {
    // 查询该人员的品牌证照信息列表 -基本信息证明
    List<Map> getBrandBasInfor(@Param("brandId") int brandId);
}
