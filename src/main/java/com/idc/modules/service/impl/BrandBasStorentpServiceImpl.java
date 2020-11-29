package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasStorentp;
import com.idc.modules.mapper.BrandBasStorentpMapper;
import com.idc.modules.service.IBrandBasStorentpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料14.品牌入库情况 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasStorentpServiceImpl extends ServiceImpl<BrandBasStorentpMapper, BrandBasStorentp> implements IBrandBasStorentpService {

    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasStorentp());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandBasStorentp> brandBasStorentps) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("brandId");
        for(BrandBasStorentp brandBasStorentp : brandBasStorentps){
            checkMap= BeanCheckUtils.checkObject(brandBasStorentp,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
