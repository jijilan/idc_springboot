package com.idc.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BackStageMapper{
    /*分页查询填报列表*/
    IPage<Map> selectBrandPage(IPage ipage,@Param("paraMap")Map<String,Object> map);
}
