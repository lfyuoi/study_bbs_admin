package com.bbs.cloud.admin.service.service;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.controller.ServiceController;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceService {

    final static  org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JsonComponentModule jsonComponentModule;

    public HttpResult sendMessage(OrderMessageParam param) {

        logger.info("进入接收订单消息接口，请求参数{}",JsonUtils.objectToJson(param));
        param.setDate(new Date());

        rabbitTemplate.convertAndSend(RabbitContant.SERVICE_EXCHANGE_NAME, RabbitContant.SERVICE_ROUTING_KEY, JsonUtils.objectToJson(param));

        return HttpResult.ok();
    }
}
