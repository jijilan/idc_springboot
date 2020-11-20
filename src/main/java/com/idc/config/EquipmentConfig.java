package com.idc.config;

import com.idc.common.equipment.entity.door.DoorCallBackEntity;
import com.idc.common.equipment.entity.gashapon.GashaponCallBackEntity;
import com.idc.common.equipment.service.EDoorService;
import com.idc.common.equipment.service.EGashaponService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description: 设备配置类
 **/
@Configuration
public class EquipmentConfig {

    @ConfigurationProperties(prefix = "equipment.door")
    @Bean
    public DoorCallBackEntity doorCallBackEntity() {
        return new DoorCallBackEntity();
    }

    @ConfigurationProperties(prefix = "equipment.gashapon")
    @Bean
    public GashaponCallBackEntity gashaponCallBackEntity() {
        return new GashaponCallBackEntity();
    }


    @ConfigurationProperties(prefix = "equipment.door")
    @Bean
    public EDoorService doorService() {
        return new EDoorService();
    }

    @ConfigurationProperties(prefix = "equipment.gashapon")
    @Bean
    public EGashaponService gashaponService() {
        return new EGashaponService();
    }
}
