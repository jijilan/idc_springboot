package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasPatent;
import com.idc.modules.entity.BrandBasStorentp;
import com.idc.modules.service.IBrandBasPatentService;
import com.idc.modules.service.IBrandBasStorentpService;
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
 * 证明材料14.品牌入库情况 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasStorentp")
@RequestMapping("/api/brand-bas-storentp/agent")
@Validated
public class BrandBasStorentpController extends BaseController {
    @Autowired
    private IBrandBasStorentpService iBrandBasStorentpService;
    private int brandId;
    /**
     * 保存证明材料14的数据
     * @param jsonStr
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBasStorentp")
    public ResultView saveBrandBasStorentp(@NotNull(message = "专利证书相关信息不能为空") String jsonStr, HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        List<BrandBasStorentp> brandBasStorentps= JSON.parseArray(jsonStr,BrandBasStorentp.class);
        Map checkMap= iBrandBasStorentpService.checkBeanListIsNull(brandBasStorentps);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        for(int i=0;i<brandBasStorentps.size();i++){
            brandBasStorentps.set(i,brandBasStorentps.get(i).setBrandId(brandId));
        }
        // 删除应用数据
        UpdateWrapper<BrandBasStorentp> brandBasStorentpUpdateWrapper = new UpdateWrapper();
        brandBasStorentpUpdateWrapper.lambda().eq(BrandBasStorentp::getBrandId,brandId);
        iBrandBasStorentpService.remove(brandBasStorentpUpdateWrapper);
        // 重新插入数据
        iBrandBasStorentpService.saveBatch(brandBasStorentps);
        return ResultView.ok();
    }

    /**
     * 获取证明材料14的信息
     * @param request
     * @return
     */
    @PostMapping(value = "/getBrandBasStorentp")
    public ResultView getBrandBasStorentp( HttpServletRequest request) {
        brandId=getBrandIdByUser(request);
        QueryWrapper<BrandBasStorentp> brandBasStorentpQueryWrapper=new QueryWrapper<>();
        brandBasStorentpQueryWrapper.lambda().eq(BrandBasStorentp::getBrandId,brandId);
        List<BrandBasStorentp> brandBasStorentps=iBrandBasStorentpService.list(brandBasStorentpQueryWrapper);
        return ResultView.ok(brandBasStorentps);
    }

}
