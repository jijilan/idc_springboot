package com.idc.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.idc.modules.model.QPage;

import java.util.Map;

public interface IBackStageService{
    IPage<Map> selectBrandPage(QPage qPage,Map paraMap);

}