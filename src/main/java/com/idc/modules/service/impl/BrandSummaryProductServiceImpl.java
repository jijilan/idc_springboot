package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.entity.BrandSummaryProduct;
import com.idc.modules.mapper.BrandSummaryProductMapper;
import com.idc.modules.service.IBrandSummaryProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 代理商、制造商 简介--相关产品 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandSummaryProductServiceImpl extends ServiceImpl<BrandSummaryProductMapper, BrandSummaryProduct> implements IBrandSummaryProductService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandSummaryProduct());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandSummaryProduct> brandSummaryProducts) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("sumaryId");
        for(BrandSummaryProduct brandSummaryProduct : brandSummaryProducts){
            checkMap= BeanCheckUtils.checkObject(brandSummaryProduct,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
