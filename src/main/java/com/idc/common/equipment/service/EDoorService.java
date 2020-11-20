package com.idc.common.equipment.service;

import com.idc.common.equipment.entity.door.DoorLightingEntity;
import com.idc.common.equipment.entity.door.DoorCallBackEntity;
import com.idc.common.equipment.entity.door.DoorOpenEntity;
import com.idc.common.utils.IdentityUtil;
import com.idc.common.utils.MD5Util;
import com.idc.common.utils.RestTemplateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description:
 **/
@Data
public class EDoorService {

    private String heartbeatRequestUrl;

    private String workControlRequestUrl;

    private String lightingControlRequestUrl;

    public static class Control {
        /**
         * 1001：开启门禁。
         */
        public static final String openDoor = "1001";
        /**
         * 2000：关闭照明灯 (如当前模块为无人管理模式时，返回失败)。
         */
        public static final String closeLamp = "2000";
        /**
         * 2001：开启照明灯 (如当前模块为无人管理模式时，返回失败)。
         */
        public static final String openLamp = "2001";
        /**
         * A000：无人管理模式。
         */
        public static final String nobodyMode = "A000";
        /**
         * A001：有人管理模式。
         */
        public static final String existPersonMode = "A001";
        /**
         * F001: 管理模式查询。
         */
        public static final String QUERY_MODE = "F001";
        /**
         * FFFF: 主动触发心跳包
         */
        public static final String TRIGGER_HEARTBEAT = "FFFF";

    }

    /**
     * 回调设置
     *
     * @param doorCallBackEntity
     * @return
     */
    public String setCallBackUrl(DoorCallBackEntity doorCallBackEntity) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>(5);
        valueMap.add("macno", doorCallBackEntity.getMacno());
        valueMap.add("sys_url", doorCallBackEntity.getSys_url());
        valueMap.add("open_url", doorCallBackEntity.getOpen_url());
        valueMap.add("sign", MD5Util.endCode(doorCallBackEntity.getSign()));
        return RestTemplateUtil.post(getHeartbeatRequestUrl(), valueMap);
    }

    /**
     * 远程开门/控制
     *
     * @param doorOpenEntity
     * @return
     */
    public String guardControl(DoorOpenEntity doorOpenEntity) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>(3);
        valueMap.add("macno", doorOpenEntity.getMacno());
        if (StringUtils.isBlank(doorOpenEntity.getSysnum())) {
            doorOpenEntity.setSysnum(IdentityUtil.getRandomNumTwo(255) + "");
        }
        valueMap.add("sysnum", doorOpenEntity.getSysnum());
        valueMap.add("setting", doorOpenEntity.getSetting());
        return RestTemplateUtil.post(getWorkControlRequestUrl(), valueMap);
    }

    /**
     * 设置设备照明时间段参数
     *
     * @param doorLightingEntity
     * @return
     */
    public String setLightingPeriod(DoorLightingEntity doorLightingEntity) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>(3);
        valueMap.add("macno", doorLightingEntity.getMacno());
        valueMap.add("start_time", doorLightingEntity.getStart_time());
        valueMap.add("end_time", doorLightingEntity.getEnd_time());
        return RestTemplateUtil.post(getLightingControlRequestUrl(), valueMap);
    }

    public static void main(String[] args) {
        EDoorService eDoorService = new EDoorService();
        eDoorService.setHeartbeatRequestUrl("http://120.77.72.190:8085/api/ZhongshanCtl/setCallBackUrl");
        eDoorService.setLightingControlRequestUrl("http://120.77.72.190:8085/api/ZhongshanCtl/setLightingPeriod");
        eDoorService.setWorkControlRequestUrl("http://120.77.72.190:8085/api/ZhongshanCtl/guardControl");

//        DoorCallBackEntity doorCallBackEntity = new DoorCallBackEntity();
//        doorCallBackEntity.setMacno("2019030414300002");
//        doorCallBackEntity.setOpen_url("https://hznobodyshop.dlc-sz.com/api/door/front/coinCallBack");
//        doorCallBackEntity.setSys_url("https://hznobodyshop.dlc-sz.com/api/door/front/heartbeatCallBack");
//        doorCallBackEntity.setSign("hzNobodayShop");
//        System.out.println("--------------------心跳回调设置start------------------------");
//        System.out.println(eDoorService.setCallBackUrl(doorCallBackEntity));
//        System.out.println("--------------------心跳回调设置end------------------------");
        //参数设置:
        // 1001：开启门禁。
        // 2000：关闭照明灯 (如当前模块为无人管理模式时，返回失败)。
        // 2001：开启照明灯 (如当前模块为无人管理模式时，返回失败)。
        // A000：无人管理模式。
        // A001：有人管理模式。
        // F001: 管理模式查询。
        // FFFF: 触发心跳包//不返回
        DoorOpenEntity doorOpenEntity = new DoorOpenEntity();
        doorOpenEntity.setMacno("2019030414300002");
        doorOpenEntity.setSetting("1001");
        doorOpenEntity.setSysnum(IdentityUtil.getRandomNumTwo(255) + "");
        System.out.println(eDoorService.guardControl(doorOpenEntity));

    }
}
