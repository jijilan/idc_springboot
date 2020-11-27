package com.idc.modules.service;

import com.idc.modules.entity.BrandBasicInfor;
import com.idc.modules.entity.BrandPerson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 内部人员信息表(法人代表、销售负责人、销售业务人员、其他业务人员) 服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
public interface IBrandPersonService extends IService<BrandPerson> {
    Map checkBeanIsNull(BrandPerson brandPerson, List<String> ignoreList);
    Map checkBeanIsNull(BrandPerson brandPerson);
    Map checkBeanListIsNull(List<BrandPerson> brandPersons);

}
