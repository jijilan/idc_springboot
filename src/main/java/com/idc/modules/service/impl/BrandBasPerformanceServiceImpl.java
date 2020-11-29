package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasPerformance;
import com.idc.modules.mapper.BrandBasPerformanceMapper;
import com.idc.modules.service.IBrandBasPerformanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料15.产品业绩情况 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasPerformanceServiceImpl extends ServiceImpl<BrandBasPerformanceMapper, BrandBasPerformance> implements IBrandBasPerformanceService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasPerformance());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandBasPerformance> brandBasPerformances) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("brandId");
        for(BrandBasPerformance brandBasPerformance : brandBasPerformances){
            checkMap= BeanCheckUtils.checkObject(brandBasPerformance,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
