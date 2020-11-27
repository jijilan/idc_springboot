package com.idc.modules.service.impl;

import com.idc.common.result.ResultView;
import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandPerson;
import com.idc.modules.mapper.BrandPersonMapper;
import com.idc.modules.service.IBrandPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 内部人员信息表(法人代表、销售负责人、销售业务人员、其他业务人员) 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandPersonServiceImpl extends ServiceImpl<BrandPersonMapper, BrandPerson> implements IBrandPersonService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandPerson());
    }

    @Override
    public Map checkBeanIsNull(BrandPerson brandPerson, List<String> ignoreList) {
        Map checkMap=new HashMap();
        checkMap= BeanCheckUtils.checkObject(brandPerson,ignoreList,checkInfor);
        return checkMap;
    }

    @Override
    public Map checkBeanIsNull(BrandPerson brandPerson) {
        List<String> ignoreList=new ArrayList<>();
        Map checkMap=new HashMap();
        checkMap=BeanCheckUtils.checkObject(brandPerson,ignoreList,checkInfor);
        return checkMap;
    }

    @Override
    public Map checkBeanListIsNull(List<BrandPerson> brandPersons) {
        Map checkMap=new HashMap();
        List<String> ignoreList=new ArrayList<>();
        ignoreList.add("brandId");
        for(BrandPerson brandPerson : brandPersons){
            checkMap=BeanCheckUtils.checkObject(brandPerson,ignoreList,checkInfor);
            if (!"true".equals(checkMap.get("status") + "")) {
                return  checkMap;
            }
        }
        return checkMap;
    }
}
