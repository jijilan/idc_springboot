package com.idc.modules.service.impl;

import com.idc.modules.entity.BrandPerson;
import com.idc.modules.mapper.BrandPersonMapper;
import com.idc.modules.service.IBrandPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
