package test;

import com.alibaba.fastjson.JSON;
import com.idc.IdcApplication;
import com.idc.common.sms.SzcSMS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

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
        Map map= JSON.parseObject(szcSMS.sendPhoneCode("18124540214",szcSMS.getMsg("123456")),Map.class);
        if("1".equals(map.get("result")+"")){
            System.out.println(map.get("tips")+"");
        }else{
            System.out.println("发送失败！"+map.get("tips"));
        }
    }
}
