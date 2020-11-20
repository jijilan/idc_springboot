//package com.idc.modules.task;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.idc.common.enums.DictionaryEnum;
//import com.idc.common.equipment.entity.door.DoorHeartbeatEntity;
//import com.idc.common.redis.RedisService;
//import com.idc.common.result.SysConstant;
//import com.idc.modules.entity.Door;
//import com.idc.modules.entity.Shop;
//import com.idc.modules.service.IDoorService;
//import com.idc.modules.service.IShopService;
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
//
///**
// * @Author: jijl
// * @Description: 定时器one
// * @Date: 2020/7/2 16:55
// **/
//@Slf4j
//@Component
//public class ScheduledOne {
//
//    private final static Logger monitorLogger = LoggerFactory.getLogger("monitor");
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private IDoorService iDoorService;
//
//    @Autowired
//    private IShopService iShopService;
//
//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void executeUploadTask() {
//        Set<Object> objectSet = redisService.sGet(SysConstant.PROJECT_NAME + SysConstant.DOOR_EQUIPMENT_NO);
//        ArrayList<Door> doorArrayList = (ArrayList<Door>) iDoorService.list();
//        if (objectSet != null && objectSet.size() > 0) {
//            long currentTime = System.currentTimeMillis();
//            for (Object obj : objectSet) {
//                DoorHeartbeatEntity doorHeartbeat = (DoorHeartbeatEntity) obj;
//                if (currentTime - Long.valueOf(doorHeartbeat.getTime()) >= 120000) {
//                    redisService.setRemove(SysConstant.PROJECT_NAME + SysConstant.DOOR_EQUIPMENT_NO, obj);
//                }
//            }
//        }
//        List<Shop> shopArrayList = new ArrayList<>();
//        objectSet = redisService.sGet(SysConstant.PROJECT_NAME + SysConstant.DOOR_EQUIPMENT_NO);
//        if (objectSet == null || objectSet.size() == 0) {
//            monitorLogger.info("所有门禁已下线");
//            QueryWrapper<Door> eqw = new QueryWrapper();
//            eqw.lambda().in(Door::getStatus, DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode(), DictionaryEnum.EQUIPMENT_STATUS_ERROR.getCode());
//            Door door = new Door();
//            door.setStatus(DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode());
//            //更新所有设备为离线状态
//            iDoorService.update(door, eqw);
//        } else {
//            monitorLogger.info("门禁设备在线数量:{}", objectSet.size());
//            for (int i = doorArrayList.size() - 1; i >= 0; i--) {
//                int count = 0;
//                for (Object obj : objectSet) {
//                    count++;
//                    DoorHeartbeatEntity doorHeartbeat = (DoorHeartbeatEntity) obj;
//                    String equipmentNo = doorArrayList.get(i).getEquipmentNo();
//                    if (equipmentNo.equals(doorHeartbeat.getMacno())
//                            && doorArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode()) {
//                        //设备重新上线
//                        monitorLogger.info("门禁:" + equipmentNo + "号设备重新上线");
//                        doorArrayList.set(i, setDoor(doorArrayList.get(i), doorHeartbeat));
//                        int mode = Integer.valueOf(doorHeartbeat.getMode());
//                        shopArrayList.add(setShop(mode, doorArrayList.get(i).getShopId()));
//                        doorArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode());
//                        break;
//                    } else if (equipmentNo.equals(doorHeartbeat.getMacno())
//                            && doorArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode()) {
//                        //设备在线
//                        if (doorHeartbeat.getMode().equals(doorArrayList.get(i).getMode().toString())) {
//                            //模式相同
//                            doorArrayList.remove(i);
//                        } else {
//                            monitorLogger.info("门禁:" + equipmentNo + "号设备当前模式与数据库不一致");
//                            doorArrayList.set(i, setDoor(doorArrayList.get(i), doorHeartbeat));
//                            //模式不相同
//                            int mode = Integer.valueOf(doorHeartbeat.getMode());
//                            shopArrayList.add(setShop(mode, doorArrayList.get(i).getShopId()));
//                        }
//                        break;
//                    } else if (equipmentNo.equals(doorHeartbeat.getMacno())
//                            && doorArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_ERROR.getCode()) {
//                        //设备在线-设备状态故障中-收到心跳-排除
//                        monitorLogger.info("门禁:" + equipmentNo + "号修改为正常");
//                        doorArrayList.set(i, setDoor(doorArrayList.get(i), doorHeartbeat));
//                        int mode = Integer.valueOf(doorHeartbeat.getMode());
//                        shopArrayList.add(setShop(mode, doorArrayList.get(i).getShopId()));
//                        doorArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_NORMAL.getCode());
//                        break;
//                    }
//                    if (count == objectSet.size()) {
//                        if (doorArrayList.get(i).getStatus() == DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode()) {
//                            doorArrayList.remove(i);
//                        } else {
//                            monitorLogger.info("门禁:" + doorArrayList.get(i).getEquipmentNo() + "号设备没有收到心跳-设置离线");
//                            doorArrayList.get(i).setStatus(DictionaryEnum.EQUIPMENT_STATUS_FAU.getCode());
//                        }
//                    }
//                }
//            }
//            monitorLogger.info("门禁需要修改的设备数量:{}", doorArrayList.size());
//            if (doorArrayList.size() > 0) {
//                if (iDoorService.updateBatchById(doorArrayList)) {
//                    if (shopArrayList.size() > 0) {
//                        iShopService.updateBatchById(shopArrayList);
//                    }
//                }
//            }
//        }
//    }
//
//    public Door setDoor(Door door, DoorHeartbeatEntity doorHeartbeat) {
//        door.setMode(Integer.valueOf(doorHeartbeat.getMode()));
//        door.setLightStatus(Integer.valueOf(doorHeartbeat.getLight_status()));
//        door.setUvStatus(Integer.valueOf(doorHeartbeat.getUv_status()));
//        door.setInfraredStatus(JSONObject.toJSONString(doorHeartbeat.getInfrared_status()));
//        door.setInfraredTamperStatus(Integer.valueOf(doorHeartbeat.getInfrared_tamper_status()));
//        return door;
//    }
//
//    public Shop setShop(int mode, Long shopId) {
//        Shop shop = new Shop();
//        shop.setShopId(shopId);
//        if (mode == DictionaryEnum.DOOR_MODE_0.getCode()) {
//            //无人模式
//            shop.setPattern(DictionaryEnum.SHOP_PATTERN_2.getCode());
//        } else {
//            //有人模式
//            shop.setPattern(DictionaryEnum.SHOP_PATTERN_1.getCode());
//        }
//        return shop;
//    }
//}
