package com.bbs.cloud.admin.service.service;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import com.bbs.cloud.admin.service.result.vo.GiftVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceService {

    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ServiceGiftMapper serviceGiftMapper;

    public HttpResult sendMessage(OrderMessageParam param) {

        logger.info("进入接收订单消息接口，请求参数{}",JsonUtils.objectToJson(param));
        param.setDate(new Date());

        rabbitTemplate.convertAndSend(RabbitContant.SERVICE_EXCHANGE_NAME, RabbitContant.SERVICE_ROUTING_KEY, JsonUtils.objectToJson(param));

        return HttpResult.ok();
    }

    public HttpResult<Integer> queryServiceGiftTotal() {
        logger.info("远程调用 ----- Start ----- 获取服务组件的礼物总数量");
        Integer total = serviceGiftMapper.queryGiftAmount();
        if (total == null) {
            total = 0;
        }
        logger.info("远程调用 ----- 获取到服务组件的礼物总数量, total:{}",total);
        return new HttpResult<>(total);

    }

    public HttpResult<String> queryServiceGiftList() {
        logger.info("远程调用 ----- Start ----- 获取服务组件的礼物列表");
        List<ServiceGiftDTO> serviceGiftDTOS = serviceGiftMapper.queryGiftDTOList();
        logger.info("远程调用 ----- 获取到服务组件的礼物列表, serviceGiftDTOS:{}",JsonUtils.objectToJson(serviceGiftDTOS));
        List<GiftVO> giftVOS =new ArrayList<>();
        serviceGiftDTOS.forEach(item -> {
            GiftVO giftVO = new GiftVO();
            BeanUtils.copyProperties(item,giftVO);
            giftVOS.add(giftVO);
        });
        return new HttpResult(JsonUtils.objectToJson(giftVOS));
    }

    public HttpResult updateServiceGiftList(String data) {
        try {
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表, data：{}",data);
            List<ServiceGiftDTO> updateServiceGiftDTOS = JsonUtils.jsonToList(data,ServiceGiftDTO.class);
            serviceGiftMapper.updateGiftDTOList(updateServiceGiftDTOS);
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表成功, data：{}",data);
        }catch (Exception e){
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表发生异常, data：{}",data);
            e.printStackTrace();
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }
}
