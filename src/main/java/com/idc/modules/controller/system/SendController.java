package com.idc.modules.controller.system;


import com.idc.common.annotation.valid.Phone;
import com.idc.common.annotation.valid.Status;
import com.idc.common.sms.ZhuTongSMS;
import com.idc.common.utils.IdentityUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: jijl
 * @Description:
 * @Date: 2020/7/2 16:48
 **/
@Slf4j
@RestController
@RequestMapping("/api/sms")
@Validated
public class SendController extends BaseController {

    @Autowired
    private ZhuTongSMS zhuTongSMS;

    /**
     * 发送手机短信
     *
     * @param phone
     * @param type
     * @return
     */
    @GetMapping("/send")
    public ResultView getPhoneCode(@Phone String phone,
                                   @Status(value = "^[1-6]$", isNotBlank = false) String type) {
        String phoneCode = IdentityUtil.getRandomNum(6);
        String messageModel = zhuTongSMS.messageModel(phoneCode, Integer.valueOf(type));
        if (zhuTongSMS.sendSMS(messageModel, phone) == 1) {
            redisService.setAuthorizedSubject(phone, phoneCode, 180);
            log.info("给" + phone + "手机号码发送验证码----->" + phoneCode);
            return ResultView.ok();
        } else {
            return ResultView.error(ResultEnum.CODE_17);
        }
    }


    public static void main(String[] args) {
        ZhuTongSMS zhuTongSMS = new ZhuTongSMS();
        String messageModel = zhuTongSMS.messageModel("21321", Integer.valueOf(1));
        zhuTongSMS.sendSMS(messageModel, "18279995501");
    }
}
