package com.idc.common.equipment.entity.door;

import lombok.Data;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/4
 * @Description: 门禁照明设置
 **/
@Data
public class DoorLightingEntity {

    private String macno;

    private String start_time;

    private String end_time;
}
