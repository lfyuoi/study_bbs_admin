package com.bbs.cloud.admin.service.message;

import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;

public interface MessageHandler {

    void handler(OrderMessageDto orderMessageDto);

    Integer getServiceType();
}
