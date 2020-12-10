package com.idc.modules.controller.agent;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idc.common.result.ResultView;
import com.idc.common.utils.DateUtils;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.ExcelUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.excle.BrandInforListExcle;
import com.idc.modules.model.QPage;
import com.idc.modules.service.IBackStageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/9
 */
@Slf4j
@RestController("BackStage")
@RequestMapping("/api/backStage/agent")
@Validated
public class BackStageController extends BaseController {
    @Autowired
    private IBackStageService iBackStageService;
    /**
     * 填报管理-条件查询
     * @param offset 当前页数
     * @param limit 每页条数
     * @param qiylx 申报企业类型
     * @param shenbqy 申报企业名称
     * @param qiyxz 企业性质
     * @param sbzt 申报状态
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandInforListByPage")
    public ResultView getBrandInforListByPage(String qiylx,String shenbqy,String qiyxz,String sbzt,int offset,int limit,HttpServletRequest request) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("shenbqy",shenbqy);
        parMap.put("qiyxz",qiyxz);
        parMap.put("sbzt",sbzt);
        IPage ipage= iBackStageService.selectBrandPage(qPage,parMap);
        return ResultView.ok(ipage);
    }
    @PostMapping(value = "/exportBrandInforListByPage")
    public void exportBrandInforListByPage(String qiylx, String shenbqy, String qiyxz, String sbzt, HttpServletRequest request, HttpServletResponse response) {
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("shenbqy",shenbqy);
        parMap.put("qiyxz",qiyxz);
        parMap.put("sbzt",sbzt);
        List<BrandInforListExcle> brandInforListExcles = iBackStageService.selectBrandPage(parMap);
        ExcelUtil.defaultExport(brandInforListExcles,BrandInforListExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }


}
