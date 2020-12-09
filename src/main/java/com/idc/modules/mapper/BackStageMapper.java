package com.idc.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface BackStageMapper{
    /*分页查询填报列表*/
    List<Map> selectBrandPage(IPage ipage,@Param("paraMap")Map<String,Object> map);
}
