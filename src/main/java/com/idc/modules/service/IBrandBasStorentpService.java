package com.idc.modules.service;

import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasStorentp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料14.品牌入库情况 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasStorentpService extends IService<BrandBasStorentp> {
    Map checkBeanListIsNull(List<BrandBasStorentp> brandBasStorentps);
}
