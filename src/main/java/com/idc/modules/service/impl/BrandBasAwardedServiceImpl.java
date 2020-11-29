package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasAwarded;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.mapper.BrandBasAwardedMapper;
import com.idc.modules.service.IBrandBasAwardedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料10.拟入库品牌产品获奖情况 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasAwardedServiceImpl extends ServiceImpl<BrandBasAwardedMapper, BrandBasAwarded> implements IBrandBasAwardedService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasAwarded());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandBasAwarded> brandBasAwardeds) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("brandId");
        for(BrandBasAwarded brandBasAwarded : brandBasAwardeds){
            checkMap= BeanCheckUtils.checkObject(brandBasAwarded,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
