package com.idc.modules.controller.front;


import com.idc.common.equipment.entity.gashapon.GashaponOpenEntity;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.modules.controller.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jijl
 * @since 2019-03-05
 */
@RestController("frontGashapon")
@RequestMapping("/api/gashapon/front")
@Slf4j
public class GashaponController extends BaseController {

    @RequestMapping("/heartbeatCallBack")
    public ResultView heartbeatCallBack(GashaponOpenEntity gashaponOpenEntity) {
        log.info("心跳回调:{}", gashaponOpenEntity.toString());
        long currentTime = System.currentTimeMillis();
        Set<Object> objectSet = redisService.sGet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO);
        gashaponOpenEntity.setTime(currentTime + "");
        if (objectSet != null && objectSet.size() > 0) {
            int count = 0;
            for (Object obj : objectSet) {
                GashaponOpenEntity gashaponOpen = (GashaponOpenEntity) obj;
                if (gashaponOpen.getMacno().equals(gashaponOpenEntity.getMacno())) {
                    redisService.setRemove(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO, obj);
                    if (currentTime - Long.valueOf(gashaponOpen.getTime()) < 120000) {
                        redisService.sSet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO, gashaponOpenEntity);
                    }
                } else {
                    count++;
                }
            }
            if (count == objectSet.size()) {
                redisService.sSet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO, gashaponOpenEntity);
            }
        } else {
            redisService.sSet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO, gashaponOpenEntity);
        }
        return ResultView.ok();
    }

    @RequestMapping("/coinCallBack")
    public ResultView coinCallBack(GashaponOpenEntity gashaponOpenEntity) {
        log.info("投币输出回调:{}", gashaponOpenEntity.toString());
        return ResultView.ok();
    }
}
