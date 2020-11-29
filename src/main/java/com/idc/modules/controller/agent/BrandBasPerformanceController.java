package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasPerformance;
import com.idc.modules.service.IBrandBasPatentService;
import com.idc.modules.service.IBrandBasPerformanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料15.产品业绩情况 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasPerformance")
@RequestMapping("/api/brand-bas-performance/agent")
@Validated
public class BrandBasPerformanceController extends BaseController {
    @Autowired
    private IBrandBasPerformanceService iBrandBasPerformanceService;
    private int brandId;
    /**
     * 保存证明材料15的数据
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBasPerformance")
    public ResultView saveBrandBasPerformance(@NotNull(message = "专利证书相关信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        List<BrandBasPerformance> brandBasPerformances= JSON.parseArray(jsonStr,BrandBasPerformance.class);
        Map checkMap= iBrandBasPerformanceService.checkBeanListIsNull(brandBasPerformances);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 删除应用数据
        UpdateWrapper<BrandBasPerformance> brandBasPerformanceUpdateWrapper = new UpdateWrapper();
        brandBasPerformanceUpdateWrapper.lambda().eq(BrandBasPerformance::getBrandId,brandId);
        iBrandBasPerformanceService.remove(brandBasPerformanceUpdateWrapper);
        // 重新插入数据
        iBrandBasPerformanceService.saveBatch(brandBasPerformances);
        return ResultView.ok();
    }

    /**
     * 获取证明材料9的信息
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandBasPerformance")
    public ResultView getBrandBasPerformance( HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        QueryWrapper<BrandBasPerformance> brandBasPerformanceQueryWrapper=new QueryWrapper<>();
        brandBasPerformanceQueryWrapper.lambda().eq(BrandBasPerformance::getBrandId,brandId);
        List<BrandBasPerformance> brandBasPerformances=iBrandBasPerformanceService.list(brandBasPerformanceQueryWrapper);
        return ResultView.ok(brandBasPerformances);
    }
}
