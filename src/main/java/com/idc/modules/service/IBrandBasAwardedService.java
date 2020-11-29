package com.idc.modules.service;

import com.idc.modules.entity.BrandBasAwarded;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.BrandBasPatent;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料10.拟入库品牌产品获奖情况 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasAwardedService extends IService<BrandBasAwarded> {
    Map checkBeanListIsNull(List<BrandBasAwarded> brandBasAwardeds);
}
