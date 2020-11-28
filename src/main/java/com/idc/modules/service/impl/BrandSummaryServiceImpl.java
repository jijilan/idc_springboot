package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandSummary;
import com.idc.modules.mapper.BrandSummaryMapper;
import com.idc.modules.service.IBrandSummaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 制造商、代理商 简介表 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandSummaryServiceImpl extends ServiceImpl<BrandSummaryMapper, BrandSummary> implements IBrandSummaryService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandSummary());
    }
    @Override
    public Map checkBeanIsNull(BrandSummary brandSummary) {
        List<String> ignoreList=new ArrayList<>();
        // 代理品牌的品牌制造商名称
        Map checkMap=new HashMap();
        checkMap= BeanCheckUtils.checkObject(brandSummary,ignoreList,checkInfor);
        return checkMap;
    }
}
