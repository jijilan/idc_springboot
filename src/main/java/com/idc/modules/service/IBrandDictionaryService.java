package com.idc.modules.service;

import com.idc.modules.entity.BrandDictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-11-23
 */
public interface IBrandDictionaryService extends IService<BrandDictionary> {
    List<Map> getNoSonDicStruct(@Param("pCode") String pCode);
}
