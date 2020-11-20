package com.idc.common.equipment.entity.gashapon;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 扭蛋机投币实体
 **/
@Data
public class GashaponOpenEntity implements Serializable {

    /**
     * 设备号(输出参数)
     */
    private String macno;
    /**
     * 设备序号 1-6（输出参数）
     */
    private String device_num;
    /**
     * 开门流水(0-255) 注：连续两次通信，如果是相同流水号的，不重复输出。设备只判断连续2次的流水号，不记录太长时间的流水号（输出参数）
     */
    private String sysnum;
    /**
     * 投币数 暂定范1-200，超限报失败（输出参数）
     */
    private String num;
    /**
     * 操作结果 0：执行成功 1：系统繁忙 2: 流水相同 3: 参数错误 其他: 操作错误
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

    /**
     * 6路扭蛋设备输出状态,每一位表示一个设备的状态(共6路，按顺序1-6),为0：表示设备空闲，为1表示设备繁忙
     */
    private Integer[] status;
}
