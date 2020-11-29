package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasAftersale;
import com.idc.modules.entity.BrandBasCreQua;
import com.idc.modules.mapper.BrandBasAftersaleMapper;
import com.idc.modules.service.IBrandBasAftersaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料11（售后服务机构地理位置）,12（售后响应时间）,13（免费维保期）,16（售后方案）,17（申办材料真实性承诺书） 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasAftersaleServiceImpl extends ServiceImpl<BrandBasAftersaleMapper, BrandBasAftersale> implements IBrandBasAftersaleService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasAftersale());
    }
    @Override
    public List<Map> getBrandBasLicense(int brandId) {
        return baseMapper.getBrandBasInfor(brandId);
    }

    @Override
    public Map checkBeanIsNull(BrandBasAftersale brandBasAftersale, List<String> checkList) {
        Map checkMap=new HashMap();
        checkMap= BeanCheckUtils.checkObjectByParms(brandBasAftersale,checkList,checkInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  checkMap;
        }
        return checkMap;
    }
}
