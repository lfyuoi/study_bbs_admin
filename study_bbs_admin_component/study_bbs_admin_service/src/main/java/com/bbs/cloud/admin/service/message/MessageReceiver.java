package com.bbs.cloud.admin.service.message;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageReceiver {

    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private List<MessageHandler> messageHandlers;

    @RabbitListener(queues = RabbitContant.SERVICE_QUEUE_NAME)
    public void receiver(String message) {
        logger.info("接收到订单成功消息：{}", message);
        if (StringUtils.isEmpty(message)) {
            logger.info("接受到订单消息成功，消息为空:{}", message);
            return;
        }
        OrderMessageDto orderMessageDto;
        try {
            orderMessageDto = JsonUtils.jsonToPojo(message, OrderMessageDto.class);
            if (orderMessageDto == null) {
                logger.info("接受到订单消息成功，消息转换为空:{}", message);
                return;
            }
            ;
        } catch (Exception e) {
            logger.info("接受到订单消息成功，消息转换异常:{}", message);
            e.printStackTrace();
            return;
        }
        Integer serviceType = orderMessageDto.getServiceType();

        messageHandlers.stream()
                .filter(item -> item.getServiceType().equals(serviceType))
                .findFirst().get()
                .handler(orderMessageDto);

    }
}
