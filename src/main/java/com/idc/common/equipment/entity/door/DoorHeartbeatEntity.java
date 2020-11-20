package com.idc.common.equipment.entity.door;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 门禁心跳实体
 **/
@Data
public class DoorHeartbeatEntity implements Serializable {
    /**
     * 设备号
     */
    private String macno;
    /**
     * 当前流水
     */
    private String sysnum;
    /**
     * 当前管理模式 0：无人管理模式 1：有人管理模式
     */
    private String mode;
    /**
     * 照明灯开关状态 0：关 1：开
     */
    private String light_status;
    /**
     * 紫外线灯开关状态 0：关 1：开
     */
    private String uv_status;
    /**
     * 红外栅栏状态，8路红外栅栏信号检测，作为判断是否有人在无人区。0：无人 1：有人
     */
    private Integer[] infrared_status;
    /**
     * 红外栅栏防拆状态 0：正常 1：拆除
     */
    private String infrared_tamper_status;
    /**
     * 时间戳
     */
    private String time;
    /**
     * 自定义签名
     */
    private String sign;
}
