package com.idc.common.equipment.service;

import com.idc.common.equipment.entity.gashapon.GashaponCallBackEntity;
import com.idc.common.equipment.entity.gashapon.GashaponOpenEntity;
import com.idc.common.utils.IdentityUtil;
import com.idc.common.utils.MD5Util;
import com.idc.common.utils.RestTemplateUtil;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @auther: jijl
 * @Date: Create in 2019/3/2
 * @Description:
 **/
@Data
public class EGashaponService {

    private String heartbeatRequestUrl;

    private String controlRequestUrl;

    /**
     * 心跳设备
     *
     * @param gashaponCallBackEntity
     * @return
     */
    public String setCallBackUrl(GashaponCallBackEntity gashaponCallBackEntity) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>(4);
        valueMap.add("macno", gashaponCallBackEntity.getMacno());
        valueMap.add("sys_url", gashaponCallBackEntity.getSys_url());
        valueMap.add("open_url", gashaponCallBackEntity.getOpen_url());
        valueMap.add("sign", MD5Util.endCode(gashaponCallBackEntity.getSign()));
        return RestTemplateUtil.post(getHeartbeatRequestUrl(), valueMap);
    }

    /**
     * 投币器输出
     *
     * @param gashaponOpenEntity
     * @return
     */
    public String coin(GashaponOpenEntity gashaponOpenEntity) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>(4);
        valueMap.add("macno", gashaponOpenEntity.getMacno());
        valueMap.add("device_num", gashaponOpenEntity.getDevice_num());
        valueMap.add("sysnum", gashaponOpenEntity.getSysnum());
        valueMap.add("num", gashaponOpenEntity.getNum());
        return RestTemplateUtil.post(getControlRequestUrl(), valueMap);
    }

    public static void main(String[] args) {
        //##心跳请求地址
        String heartbeatRequestUrl = "http://120.77.72.190:8085/api/ZhongshanCtl/setCallBackUrl";
        //##控制请求地址
        String controlRequestUrl = "http://120.77.72.190:8085/api/ZhongshanCtl/coin";
        EGashaponService eGashaponService = new EGashaponService();
        eGashaponService.setHeartbeatRequestUrl(heartbeatRequestUrl);
        eGashaponService.setControlRequestUrl(controlRequestUrl);
//        GashaponCallBackEntity gashaponCallBackEntity = new GashaponCallBackEntity();
//        gashaponCallBackEntity.setMacno("2019030616300002");
//        gashaponCallBackEntity.setOpen_url("https://hznobodyshop.dlc-sz.com/api/gashapon/front/coinCallBack");
//        gashaponCallBackEntity.setSys_url("https://hznobodyshop.dlc-sz.com/api/gashapon/front/heartbeatCallBack");
//        gashaponCallBackEntity.setSign("hzNobodayShop");
//        System.out.println("--------------------心跳回调设置start------------------------");
//        System.out.println(eGashaponService.setCallBackUrl(gashaponCallBackEntity));
//        System.out.println("--------------------心跳回调设置end------------------------");
        GashaponOpenEntity gashaponOpenEntity = new GashaponOpenEntity();
        gashaponOpenEntity.setMacno("2019030616300001");
        gashaponOpenEntity.setDevice_num("1");
        gashaponOpenEntity.setSysnum(IdentityUtil.getRandomNumTwo(255) + "");
        gashaponOpenEntity.setNum("2");
        System.out.println("--------------------投币设置start------------------------");
        System.out.println(eGashaponService.coin(gashaponOpenEntity));
        System.out.println("--------------------投币设置end------------------------");

    }
}
