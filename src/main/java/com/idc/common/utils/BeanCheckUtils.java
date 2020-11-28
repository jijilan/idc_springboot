package com.idc.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人: Dylan
 * @创建时间: 2020/8/12
 * @描述
 */
public class BeanCheckUtils {

    /**
     *
     * @param obj 需要检测的数据对象
     * @param ignoreParms 需要忽略的对象名称
     * @return 返回
     */
    public static Map<String,Object> checkObject(Object obj, List<String> ignoreParms,Map<String,Object> checkInfor){
        ignoreParms.add("id");
        ignoreParms.add("addTime");
        ignoreParms.add("updateTime");
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                String propetryName=f.getName();
                // 排除忽略的字段
                if(!ignoreParms.contains(propetryName)){
                    if(EmptyUtil.isEmpty(f.get(obj))){
                        return getResMap(false,checkInfor.get(propetryName)+"");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return getResMap(true,"数据正常");
    }
    public static Map<String,Object> checkObjectByParms(Object obj, List<String> parms,Map<String,Object> checkInfor){
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                String propetryName=f.getName();
                // 只判断指定的字段
                if(parms.contains(propetryName)){
                    if(EmptyUtil.isEmpty(f.get(obj))){
                        return getResMap(false,checkInfor.get(propetryName)+"");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return getResMap(true,"数据正常");
    }
    private static Map<String,Object> getResMap(boolean checkStatus, String memo){
        Map<String,Object> resMap=new HashMap<>();
        resMap.put("status",checkStatus);
        resMap.put("memo",memo);
        return resMap;
    }
    public static Map<String,Object> getCheckInfor(Object obj){
        Map<String,Object> checkInfor=new HashMap<>();
        for(Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            if(EmptyUtil.isNotEmpty(annotation)){
                checkInfor.put(field.getName(),annotation.value()+",不能为空!");
            }else{
                checkInfor.put(field.getName(),field.getName()+",不能为空!");
            }
        }
        return checkInfor;
    }

    public static void main(String[] args) {

    }
}
