package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idc.modules.mapper.BackStageMapper;
import com.idc.modules.model.QPage;
import com.idc.modules.service.IBackStageService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/9
 */
@Service
public class BackStageServiceImpl implements IBackStageService {

    @Override
    public IPage<Map> selectBrandPage(QPage qPage, Map paraMap) {

        return null;
    }
}