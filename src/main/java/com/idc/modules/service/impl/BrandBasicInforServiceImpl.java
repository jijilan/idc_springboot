package com.idc.modules.service.impl;

import com.idc.common.utils.BeanCheckUtils;
import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.mapper.BrandBasicInforMapper;
import com.idc.modules.service.IBrandBasicInforService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 基础信息表(代理商、品牌商) 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Service
public class BrandBasicInforServiceImpl extends ServiceImpl<BrandBasicInforMapper, BrandBasicInfor> implements IBrandBasicInforService {
    private static Map<String,Object> checkInfor=new HashMap<>();
    static{
        // 获取检查列表
        checkInfor= BeanCheckUtils.getCheckInfor(new BrandBasicInfor());
    }

    @Override
    public Map checkBeanIsNull(BrandBasicInfor brandBasicInfor, List<String> ignoreList) {
        // 代理品牌的品牌制造商名称
        ignoreList.add("dailppzzsmc");
        // 传真号码为空
        ignoreList.add("chuanzhm");
        // 申报材料真实性承诺书
        ignoreList.add("chengns");
        Map checkMap=new HashMap();
        checkMap=BeanCheckUtils.checkObject(brandBasicInfor,ignoreList,checkInfor);
        return checkMap;
    }
    @Override
    public Map checkBeanIsNull(BrandBasicInfor brandBasicInfor) {
        List<String> ignoreList=new ArrayList<>();
        // 代理品牌的品牌制造商名称
        ignoreList.add("dailppzzsmc");
        // 传真号码为空
        ignoreList.add("chuanzhm");
        // 申报材料真实性承诺书
        ignoreList.add("chengns");
        Map checkMap=new HashMap();
        checkMap=BeanCheckUtils.checkObject(brandBasicInfor,ignoreList,checkInfor);
        return checkMap;
    }
}
