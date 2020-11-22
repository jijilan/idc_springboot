package com.idc.modules.service.impl;

import com.idc.modules.entity.CompanyType;
import com.idc.modules.mapper.CompanyTypeMapper;
import com.idc.modules.service.ICompanyTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 制造商和代理商表 服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-22
 */
@Service
public class CompanyTypeServiceImpl extends ServiceImpl<CompanyTypeMapper, CompanyType> implements ICompanyTypeService {

}
