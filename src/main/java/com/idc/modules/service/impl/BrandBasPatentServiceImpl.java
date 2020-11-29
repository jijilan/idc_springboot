package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.mapper.BrandBasPatentMapper;
import com.idc.modules.service.IBrandBasPatentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料9：拟入库品牌产品相关的专利证书提供专利复印件 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasPatentServiceImpl extends ServiceImpl<BrandBasPatentMapper, BrandBasPatent> implements IBrandBasPatentService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasPatent());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandBasPatent> brandBasPatents) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("brandId");
        for(BrandBasPatent brandBasPatent : brandBasPatents){
            checkMap= BeanCheckUtils.checkObject(brandBasPatent,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
