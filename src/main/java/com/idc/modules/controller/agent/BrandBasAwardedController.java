package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasAwarded;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.service.IBrandBasAwardedService;
import com.idc.modules.service.IBrandBasPatentService;
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
 * 证明材料10.拟入库品牌产品获奖情况 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasAwarded")
@RequestMapping("/api/brand-bas-awarded/agent")
@Validated
public class BrandBasAwardedController extends BaseController {
    @Autowired
    private IBrandBasAwardedService iBrandBasAwardedService;
    private int brandId;
    /**
     * 保存证明材料10的数据
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBasAwarded")
    public ResultView saveBrandBasAwarded(@NotNull(message = "获奖相关信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        List<BrandBasAwarded> brandBasAwardeds= JSON.parseArray(jsonStr,BrandBasAwarded.class);
        Map checkMap= iBrandBasAwardedService.checkBeanListIsNull(brandBasAwardeds);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        // 删除应用数据
        UpdateWrapper<BrandBasAwarded> brandBasAwardedUpdateWrapper = new UpdateWrapper();
        brandBasAwardedUpdateWrapper.lambda().eq(BrandBasAwarded::getBrandId,brandId);
        iBrandBasAwardedService.remove(brandBasAwardedUpdateWrapper);
        // 重新插入数据
        iBrandBasAwardedService.saveBatch(brandBasAwardeds);
        return ResultView.ok();
    }

    /**
     * 获取证明材料10的信息
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandBasAwarded")
    public ResultView getBrandBasAwarded( HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        QueryWrapper<BrandBasAwarded> brandBasAwardedQueryWrapper=new QueryWrapper<>();
        brandBasAwardedQueryWrapper.lambda().eq(BrandBasAwarded::getBrandId,brandId);
        List<BrandBasAwarded> brandBasAwardeds=iBrandBasAwardedService.list(brandBasAwardedQueryWrapper);
        return ResultView.ok(brandBasAwardeds);
    }

}
