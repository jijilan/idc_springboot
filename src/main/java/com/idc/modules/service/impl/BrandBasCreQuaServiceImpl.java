package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasCreQua;
import com.idc.modules.entity.BrandBasRevPerf;
import com.idc.modules.mapper.BrandBasCreQuaMapper;
import com.idc.modules.service.IBrandBasCreQuaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料3（企业信用）、4（产品质量）、6（建筑面积）、7（管理体系认证）、8(市场占有率) 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasCreQuaServiceImpl extends ServiceImpl<BrandBasCreQuaMapper, BrandBasCreQua> implements IBrandBasCreQuaService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasCreQua());
    }
    @Override
    public List<Map> getBrandBasLicense(int brandId) {
        return baseMapper.getBrandBasInfor(brandId);
    }

    @Override
    public Map checkBeanIsNull(BrandBasCreQua BrandBasCreQua, List<String> checkList) {
        Map checkMap=new HashMap();
        checkMap= BeanCheckUtils.checkObjectByParms(BrandBasCreQua,checkList,checkInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  checkMap;
        }
        return checkMap;
    }
}
