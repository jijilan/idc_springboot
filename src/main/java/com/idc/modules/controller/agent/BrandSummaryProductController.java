package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSONObject;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 品牌 代理商、制造商 简介--相关产品 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/brand-summary-product")
public class BrandSummaryProductController   extends BaseController {
    @PostMapping(value = "/save")
    public ResultView save(@NotNull(message = "必要信息不能为空") String jsonStr, HttpServletRequest request) {
        userId=Integer.parseInt(request.getAttribute(SysConstant.USER_ID)+"");
        return  ResultView.error("操作失败");
    }
    @PostMapping(value = "/get")
    public ResultView get(HttpServletRequest request) {
        userId=Integer.parseInt(request.getAttribute(SysConstant.USER_ID)+"");
        return  ResultView.error("操作失败");
    }
}
