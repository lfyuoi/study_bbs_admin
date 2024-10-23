package com.bbs.cloud.admin.test.service;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.test.mapper.TestMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public HttpResult queryTest(){
        String id = testMapper.queryTest();
        return new HttpResult(id);
    }

    @RabbitListener(queues = RabbitContant.TEST_QUEUE_NAME)
    public void listenMessage(String message){
        System.out.println("接收消息：" + message);
    }
}
