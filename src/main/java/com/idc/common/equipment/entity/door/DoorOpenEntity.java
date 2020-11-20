package com.idc.common.equipment.entity.door;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 门禁开门实体
 **/
@Data
public class DoorOpenEntity implements Serializable {
    /**
     * 设备号（输出参数）
     */
    private String macno;
    /**
     * 开门流水(0-255) 注：连续两次通信，如果是相同流水号的，不重复输出。设备只判断连续2次的流水号，不记录太长时间的流水号（输出参数）
     */
    private String sysnum;
    /**
     * 参数设置:1001：开启门禁。2000：关闭照明灯 (如当前模块为无人管理模式时，返回失败)。2001：开启照明灯 (如当前模块为无人管理模式时，返回失败)。
     * A000：无人管理模式。A001：有人管理模式。F001: 管理模式查询。FFFF: 触发心跳包//不返回
     * (输出参数)
     */
    private String setting;
    /**
     * 设备模式 0：无人管理模式 1：有人管理模式
     */
    private String mode;
    /**
     * 操作结果 0：执行成功 1：执行失败 2: 流水相同 3: 参数错误 其他: 操作错误
     */
    private String result;
    /**
     * 时间戳
     */
    private String time;
    /**
     * 自定义签名
     */
    private String sign;
}
