package com.idc.modules.mapper;

import com.idc.modules.entity.BrandDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-11-23
 */
public interface BrandDictionaryMapper extends BaseMapper<BrandDictionary> {
    List<Map> getNoSonDicStruct(@Param("pCode") String pCode,@Param("findParent") String findParent);
    List<Map> getSonDicByPCode(@Param("pCode") String pCode);
}
