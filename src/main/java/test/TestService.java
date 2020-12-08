package test;

import com.idc.IdcApplication;
import com.idc.common.sms.SzcSMS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @创建人: Dylan
 * @创建时间: 2020/11/16
 * @描述
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdcApplication.class)
public class TestService {
    @Autowired
    private SzcSMS szcSMS;
    @Test
    public void contextLoads() {
        System.out.println(szcSMS.sendPhoneCode("18124540214","您的手机验证码是：123456"));
    }
}
