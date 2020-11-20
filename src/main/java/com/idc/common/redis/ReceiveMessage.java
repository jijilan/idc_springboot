package com.idc.common.redis;

import com.idc.common.equipment.entity.door.DoorCallBackEntity;
import com.idc.common.equipment.service.EDoorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/**
 * 接收消息
 */
@Slf4j
public class ReceiveMessage {
    @Autowired
    private DoorCallBackEntity doorCallBackEntity;
    @Autowired
    private EDoorService eDoorService;

    private CountDownLatch latch;

    @Autowired
    public ReceiveMessage(CountDownLatch countDownLatch) {
        this.latch = countDownLatch;
    }

    public void receiveMessage(Serializable message) {
        log.info("message2接收到消息了:{}", message);


    }

    public void receiveMessage2(Serializable message) {
        log.info("message2接收到消息了:{}", message);
    }

}

