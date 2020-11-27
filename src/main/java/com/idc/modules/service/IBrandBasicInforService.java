package com.idc.modules.service;

import com.idc.modules.entity.BrandBasicInfor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 基础信息表(代理商、品牌商) 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandBasicInforService extends IService<BrandBasicInfor> {
    Map checkBeanIsNull(BrandBasicInfor brandBasicInfor, List<String> ignoreList);
    Map checkBeanIsNull(BrandBasicInfor brandBasicInfor);
}
