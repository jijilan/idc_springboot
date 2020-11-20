package com.idc.modules.controller.admin;


import com.idc.common.redis.RedisService;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@RestController("adminFinance")
@RequestMapping("/apiiiiii")
public class FinanceController {
    @Autowired
    private RedisService redisService;

    @PostMapping("/get")
    public ResultView get() {
        redisService.set("asdasfasf","safasfasf");
        //异步设置回调
        redisService.sendMessage(SysConstant.Redis.MESSAGE1, "21421421");
        return ResultView.ok();
    }
}
