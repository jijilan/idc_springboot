package com.idc.common.equipment.entity.gashapon;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 扭蛋机回调实体
 **/
@Data
public class GashaponCallBackEntity implements Serializable {

    /**
     * 设备号
     */
    private String macno;
    /**
     * 心跳回调地址
     */
    private String sys_url;
    /**
     * 投币器输出回调地址
     */
    private String open_url;
    /**
     * 自定义签名
     */
    private String sign;
}
