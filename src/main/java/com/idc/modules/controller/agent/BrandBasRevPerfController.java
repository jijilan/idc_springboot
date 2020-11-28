package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.BrandBasRevPerf;
import com.idc.modules.entity.BrandSummaryApply;
import com.idc.modules.entity.BrandSummaryProduct;
import com.idc.modules.entity.BrandUserRole;
import com.idc.modules.service.IBrandBasRevPerfService;
import com.idc.modules.service.IBrandUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 证明材料 1(基本信息证明）、2（近三年营收情况）、5（履约评价情况） 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasRevPerf")
@RequestMapping("/api/brand-bas-rev-perf/agent")
public class BrandBasRevPerfController  extends BaseController {
    @Autowired
    private IBrandBasRevPerfService iBrandBasRevPerfService;
    @Autowired
    private IBrandUserRoleService iBrandUserRoleService;

    /**
     * 保存公司基本信息证明
     * @param request
     * @return
     */
    @PostMapping(value = "/saveBrandBasLicense")
    public ResultView saveBrandBasLicense(@NotNull(message = "必要信息不能为空") String jsonStr, HttpServletRequest request) {
        List<BrandBasRevPerf> brandBasRevPerfs= JSON.parseArray(jsonStr, BrandBasRevPerf.class);
        List<String> checkFields=new ArrayList<>();
        checkFields.add("brandId");
        checkFields.add("yingyezz");
        checkFields.add("yingyezzfb");
        checkFields.add("zhuczjsjzm");
        checkFields.add("zuzjgdm");
        checkFields.add("shuiwdjz");
        Map checkMap= iBrandBasRevPerfService.checkBeanListIsNull(brandBasRevPerfs,checkFields);
        if (!"true".equals(checkMap.get("status") + "")) {
            return  ResultView.error(checkMap.get("memo") + "");
        }
        if(EmptyUtil.isEmpty(brandBasRevPerfs.get(0).getId())){
            // 如果为空,则执行新增操作
            iBrandBasRevPerfService.saveBatch(brandBasRevPerfs);
        }else{
            // 执行更新操作
            iBrandBasRevPerfService.updateBatchById(brandBasRevPerfs);
        }
        return  ResultView.ok("操作成功!");
    }

    /**
     *
     * @return
     */
    @PostMapping(value = "/getBrandBasLicense")
    public ResultView getBrandBasLicense(HttpServletRequest request) {
        int userId=Integer.parseInt(request.getAttribute(SysConstant.USER_ID)+"");
        QueryWrapper<BrandUserRole> brandUserRoleQueryWrapper=new QueryWrapper<>();
        brandUserRoleQueryWrapper.lambda().eq(BrandUserRole::getUserId,userId);
        List<BrandUserRole> userBrands= iBrandUserRoleService.list(brandUserRoleQueryWrapper);
        List<Integer> brandIds=new ArrayList<>();
        for(BrandUserRole brandUserRole:userBrands){
            brandIds.add(brandUserRole.getBrandId());
        }
        List<Map> brandLicens=iBrandBasRevPerfService.getBrandBasLicense(brandIds);
        if(EmptyUtil.isEmpty(brandLicens)){
            return ResultView.error(24,"基础信息为空！");
        }
        return  ResultView.ok(brandLicens);
    }


}
