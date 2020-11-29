package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.entity.BrandSummaryProduct;
import com.idc.modules.service.IBrandBasPatentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料9：拟入库品牌产品相关的专利证书提供专利复印件 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasPatent")
@RequestMapping("/api/brand-bas-patent/agent")
@Validated
public class BrandBasPatentController extends BaseController {
    @Autowired
    private IBrandBasPatentService iBrandBasPatentService;
    private int brandId;

    /**
     * 保存证明材料9的数据
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBasPatent")
    public ResultView saveBrandBasPatent(@NotNull(message = "专利证书相关信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        List<BrandBasPatent> brandBasPatents= JSON.parseArray(jsonStr,BrandBasPatent.class);
        Map checkMap= iBrandBasPatentService.checkBeanListIsNull(brandBasPatents);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        for(int i=0;i<brandBasPatents.size();i++){
            brandBasPatents.set(i,brandBasPatents.get(i).setBrandId(brandId));
        }
        // 删除应用数据
        UpdateWrapper<BrandBasPatent> brandBasPatentUpdateWrapper = new UpdateWrapper();
        brandBasPatentUpdateWrapper.lambda().eq(BrandBasPatent::getBrandId,brandId);
        iBrandBasPatentService.remove(brandBasPatentUpdateWrapper);
        // 重新插入数据
        iBrandBasPatentService.saveBatch(brandBasPatents);
        return ResultView.ok();
    }

    /**
     * 获取证明材料9的信息
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandBasPatent")
    public ResultView getBrandBasPatent( HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        QueryWrapper<BrandBasPatent> brandBasPatentQueryWrapper=new QueryWrapper<>();
        brandBasPatentQueryWrapper.lambda().eq(BrandBasPatent::getBrandId,brandId);
        List<BrandBasPatent> brandBasPatents=iBrandBasPatentService.list(brandBasPatentQueryWrapper);
        return ResultView.ok(brandBasPatents);
    }

}
