package com.idc.common.sms;

import cn.hutool.http.HttpUtil;
import com.aliyuncs.utils.HttpsUtils;
import com.idc.common.utils.DateUtils;
import com.idc.common.utils.IdentityUtil;
import com.idc.common.utils.MD5Util;
import com.idc.mvc.resources.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther Dyaln
 * @Date 2020/12/8
 */
@Slf4j
@Component
public class SzcSMS {
    @Autowired
    private WebResource webResource;

    public String sendPhoneCode(String phone,String content){
        String szcSmsUrl=webResource.getSzcSmsUrl();
        String eprId=webResource.getSmsEprId();
        String userId=webResource.getSmsUserId();
        long timestamp=System.currentTimeMillis();
        String key=MD5Util.getMd5CodeNo(eprId+userId+webResource.getSmsPassword()+timestamp);
//        try {
//            content=java.net.URLEncoder.encode(content, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Map parMap=new HashMap();
        parMap.put("cmd","send");
        parMap.put("eprId",eprId);
        parMap.put("userId",userId);
        parMap.put("timestamp",timestamp);
        parMap.put("key",key);
        parMap.put("msgId", DateUtils.getCurrentDate("yyyyMMddHHmmss")+ IdentityUtil.getRandomNum(4));
        parMap.put("msgId","format");
        parMap.put("mobile",phone);
        parMap.put("content",content);
        return HttpUtil.post(szcSmsUrl,parMap);
    }

    public String getMsg(String phoneCode){
        return "您的验证码是【"+phoneCode+"】，在【3】分钟内有效。如非本人操作请忽略本短信。";
    }

}
