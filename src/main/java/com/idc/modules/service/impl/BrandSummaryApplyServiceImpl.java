package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandPerson;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.mapper.BrandSummaryApplyMapper;
import com.idc.modules.service.IBrandSummaryApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌简介-所申报品牌产品应用情况 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-28
 */
@Service
public class BrandSummaryApplyServiceImpl extends ServiceImpl<BrandSummaryApplyMapper, BrandSummaryApply> implements IBrandSummaryApplyService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandSummaryApply());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandSummaryApply> brandSummaryApplies) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("sumaryId");
        for(BrandSummaryApply brandSummaryApply : brandSummaryApplies){
            checkMap= BeanCheckUtils.checkObject(brandSummaryApply,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
