package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idc.modules.entity.excle.BrandInforListExcle;
import com.idc.modules.mapper.BackStageMapper;
import com.idc.modules.model.QPage;
import com.idc.modules.service.IBackStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/9
 */
@Service
public class BackStageServiceImpl implements IBackStageService {
    @Autowired
    private BackStageMapper backStageMapper;
    @Override
    public IPage<Map> selectBrandPage(QPage qPage, Map paraMap) {
        IPage ipage = new Page(qPage.getOffset(), qPage.getLimit());
        ipage.setRecords(backStageMapper.selectBrandPage(ipage, paraMap));
        return ipage;
    }

    @Override
    public List<BrandInforListExcle> selectBrandPage(Map paraMap) {
        return backStageMapper.selectBrandPage(paraMap);
    }
}
