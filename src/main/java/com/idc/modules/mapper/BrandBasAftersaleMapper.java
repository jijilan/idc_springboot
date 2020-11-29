package com.idc.modules.mapper;

import com.idc.modules.entity.BrandBasAftersale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书） Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface BrandBasAftersaleMapper extends BaseMapper<BrandBasAftersale> {
    // 查询该人员的品牌证照信息列表 -基本信息证明
    List<Map> getBrandBasInfor(@Param("brandId") int brandId);
}
