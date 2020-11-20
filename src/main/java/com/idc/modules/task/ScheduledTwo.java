//package com.idc.modules.task;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.idc.common.enums.DictionaryEnum;
//import com.idc.common.equipment.entity.door.DoorHeartbeatEntity;
//import com.idc.common.equipment.entity.gashapon.GashaponOpenEntity;
//import com.idc.common.redis.RedisService;
//import com.idc.common.result.SysConstant;
//import com.idc.modules.entity.Door;
//import com.idc.modules.entity.Gashapon;
//import com.idc.modules.entity.Shop;
//import com.idc.modules.service.IGashaponService;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * @Author: jijl
// * @Description: 定时器two
// * @Date: 2020/7/2 16:55
// **/
//@Slf4j
//@Component
//public class ScheduledTwo {
//
//    private final static Logger monitorLogger = LoggerFactory.getLogger("monitor");
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private IGashaponService iGashaponService;
//
//
//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void executeUploadTask() {
//        Set<Object> objectSet = redisService.sGet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO);
//        ArrayList<Gashapon> gashaponArrayList = (ArrayList<Gashapon>) iGashaponService.list();
//        log.info("扭蛋机有" + objectSet.size() + "在线");
//        log.info("数据库有" + gashaponArrayList.size() + "台扭蛋机");
//        if (objectSet != null && objectSet.size() > 0) {
//            long currentTime = System.currentTimeMillis();
//            for (Object obj : objectSet) {
//                GashaponOpenEntity gashaponOpenEntity = (GashaponOpenEntity) obj;
//                if (currentTime - Long.valueOf(gashaponOpenEntity.getTime()) >= 120000) {
//                    redisService.setRemove(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO, obj);
//                }
//            }
//        }
//        objectSet = redisService.sGet(SysConstant.PROJECT_NAME + SysConstant.GASHAPON_EQUIPMENT_NO);
//        if (objectSet == null || objectSet.size() == 0) {
//            monitorLogger.info("所有扭蛋机已下线");
//            log.info("所有扭蛋机已下线");
//            QueryWrapper<Gashapon> eqw = new QueryWrapper();
//            eqw.lambda().in(Gashapon::getStatus, DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode(), DictionaryEnum.EQUIPMENT_STATUS_ERROR.getCode());
//            Gashapon gashapon = new Gashapon();
//            gashapon.setStatus(DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode());
//            //更新所有设备为离线状态
//            iGashaponService.update(gashapon, eqw);
//        } else {
//            monitorLogger.info("扭蛋机设备在线数量:{}", objectSet.size());
//            log.info("扭蛋机设备在线数量:{}", objectSet.size());
//            for (int i = gashaponArrayList.size() - 1; i >= 0; i--) {
//                int count = 0;
//                for (Object obj : objectSet) {
//                    count++;
//                    GashaponOpenEntity gashaponOpenEntity = (GashaponOpenEntity) obj;
//                    String equipmentNo = gashaponArrayList.get(i).getEquipmentNo();
//                    if (equipmentNo.equals(gashaponOpenEntity.getMacno())
//                            && gashaponArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode()) {
//                        //设备重新上线
//                        monitorLogger.info("扭蛋机:" + equipmentNo + "号设备重新上线");
//                        log.info("扭蛋机:" + equipmentNo + "号设备重新上线");
//                        gashaponArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode());
//                        break;
//                    } else if (equipmentNo.equals(gashaponOpenEntity.getMacno())
//                            && gashaponArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode()) {
//                        gashaponArrayList.remove(i);
//                        break;
//                    } else if (equipmentNo.equals(gashaponOpenEntity.getMacno())
//                            && gashaponArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_ERROR.getCode()) {
//                        //设备在线-设备状态故障中-收到心跳-排除
//                        monitorLogger.info("扭蛋机:" + equipmentNo + "号修改为正常");
//                        log.info("扭蛋机:" + equipmentNo + "号修改为正常");
//                        gashaponArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode());
//                        break;
//                    }
//                    if (count == objectSet.size()) {
//                        if (gashaponArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode()) {
//                            gashaponArrayList.remove(i);
//                        } else {
//                            monitorLogger.info("扭蛋机:" + gashaponArrayList.get(i).getEquipmentNo() + "号设备没有收到心跳-设置离线");
//                            log.info("扭蛋机:" + gashaponArrayList.get(i).getEquipmentNo() + "号设备没有收到心跳-设置离线");
//                            gashaponArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode());
//                        }
//                    }
//                }
//            }
//            monitorLogger.info("扭蛋机需要修改的设备数量:{}", gashaponArrayList.size());
//            log.info("扭蛋机需要修改的设备数量:{}", gashaponArrayList.size());
//            if (gashaponArrayList.size() > 0) {
//                iGashaponService.updateBatchById(gashaponArrayList);
//            }
//        }
//    }
//}
