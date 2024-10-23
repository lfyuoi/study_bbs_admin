package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.enums.gift.GiftEnum;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;
@Component
public class GiftOrderMessageHandler implements MessageHandler {

    final static  org.slf4j.Logger logger = LoggerFactory.getLogger(GiftOrderMessageHandler.class);

    @Autowired
    private ServiceGiftMapper serviceGiftMapper;


    @Override
    public void handler(OrderMessageDto orderMessageDto) {
        logger.info("开始处理礼物服务订单,message={}", JsonUtils.objectToJson(orderMessageDto));
        try {
            Map<Integer, GiftEnum> giftsMap = GiftEnum.getGiftsMap();
            for (GiftEnum giftEnum : giftsMap.values()) {
                Integer giftType = giftEnum.getGiftType();
                ServiceGiftDTO serviceGiftDTO = serviceGiftMapper.queryGiftDTO(giftType);
                if (serviceGiftDTO == null) {
                    serviceGiftDTO= new ServiceGiftDTO();
                    serviceGiftDTO.setGiftType(giftType);
                    serviceGiftDTO.setId(CommonUtil.createUUID());
                    serviceGiftDTO.setAmount(ServiceContant.DEFAULT_SERVICE_GIFT_AMOUNT);
                    serviceGiftDTO.setUnusedAmount(ServiceContant.DEFAULT_SERVICE_UNUSED_GIFT_AMOUNT);
                    serviceGiftDTO.setUsedAmount(ServiceContant.DEFAULT_SERVICE_USED_GIFT_AMOUNT);
                    serviceGiftMapper.insertGiftDTO(serviceGiftDTO);
                }else {
                    /**
                     * TODO: 去查询活动礼物的情况，来进行库存更新
                     */
                }
            }
        } catch (Exception e) {
            logger.error("开始处理礼物服务订单,发生异常,message={}", JsonUtils.objectToJson(orderMessageDto));
            e.printStackTrace();
        }
    }

    @Override
    public Integer getServiceType() {
        return ServiceTypeEnum.GIFT_MESSAGE.getType();
    }
}
