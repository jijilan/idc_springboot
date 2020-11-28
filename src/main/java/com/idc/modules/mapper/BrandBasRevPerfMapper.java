package com.idc.modules.mapper;

import com.idc.modules.entity.BrandBasRevPerf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况） Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface BrandBasRevPerfMapper extends BaseMapper<BrandBasRevPerf> {
    // 查询该人员的品牌证照信息列表 -基本信息证明
    List<Map> getBrandBasLicense(@Param("list") List<Integer> brandIds);

}
