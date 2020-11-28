package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasRevPerf;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.mapper.BrandBasRevPerfMapper;
import com.idc.modules.service.IBrandBasRevPerfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况） 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasRevPerfServiceImpl extends ServiceImpl<BrandBasRevPerfMapper, BrandBasRevPerf> implements IBrandBasRevPerfService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasRevPerf());
    }
    @Override
    public Map checkBeanListIsNull(List<BrandBasRevPerf> brandBasRevPerfs, List<String> checkList) {
        Map checkMap=new HashMap();
        for(BrandBasRevPerf brandBasRevPerf : brandBasRevPerfs){
            checkMap= BeanCheckUtils.checkObjectByParms(brandBasRevPerf,checkList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }

    @Override
    public List<Map> getBrandBasLicense(List<Integer> brandIds){
        return baseMapper.getBrandBasLicense(brandIds);
    }
}
