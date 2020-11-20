package com.idc.common.equipment.entity.door;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 门禁回调实体
 **/
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class DoorCallBackEntity {

    /**
     * 设备号
     */
    private String macno;
    /**
     * 心跳回调地址
     */
    private String sys_url;
    /**
     * 远程开门回调地址
     */
    private String open_url;
    /**
     * 自定义签名
     */
    private String sign;
}
