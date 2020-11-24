package com.idc.modules.service.impl;

import com.idc.modules.entity.BrandDictionary;
import com.idc.modules.mapper.BrandDictionaryMapper;
import com.idc.modules.service.IBrandDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-23
 */
@Service
public class BrandDictionaryServiceImpl extends ServiceImpl<BrandDictionaryMapper, BrandDictionary> implements IBrandDictionaryService {
    @Override
    public List<Map> getNoSonDicStruct(String pCode,String findParent){
        return baseMapper.getNoSonDicStruct(pCode,findParent);
    }
    @Override
    public List<Map> getSonDicByPCode(String pCode){
        return baseMapper.getSonDicByPCode(pCode);
    }
}
