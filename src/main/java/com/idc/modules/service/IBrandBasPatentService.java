package com.idc.modules.service;

import com.idc.modules.entity.BrandBasPatent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.entity.BrandSummaryApply;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料9：拟入库品牌产品相关的专利证书提供专利复印件 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasPatentService extends IService<BrandBasPatent> {
    Map checkBeanListIsNull(List<BrandBasPatent> brandBasPatents);
}
