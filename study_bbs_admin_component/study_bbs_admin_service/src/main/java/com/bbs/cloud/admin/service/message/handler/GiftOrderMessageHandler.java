package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.enums.gift.GiftEnum;
import com.bbs.cloud.admin.common.error.CommonExceptionEnum;
import com.bbs.cloud.admin.common.feigh.client.ActivityFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.ConnectException;
import java.util.Map;
@Component
public class GiftOrderMessageHandler implements MessageHandler {

    final static Logger logger = LoggerFactory.getLogger(GiftOrderMessageHandler.class);

    @Autowired
    private ServiceGiftMapper serviceGiftMapper;

    @Autowired
    private ActivityFeighClient activityFeighClient;

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
                    continue;
                }else {
                    logger.info("开始处理礼物服务订单-整理库存,message={}", JsonUtils.objectToJson(serviceGiftDTO));
                    HttpResult<Integer> result = activityFeighClient.queryServiceGiftTotal(giftType);
                    if (result == null ||!result.getCode().equals(CommonExceptionEnum.SUCCESS.getCode())|| result.getData() == null) {
                        logger.error("远程获取活动组件礼物的使用情况,发生异常,礼物信息={}", JsonUtils.objectToJson(serviceGiftDTO));
                        result.setCode(0);
                    }
                    Integer usedActivityGiftAmount = result.getData();
                    Integer amount = serviceGiftDTO.getAmount() + ServiceContant.DEFAULT_SERVICE_GIFT_AMOUNT;
                    Integer usedAmount = usedActivityGiftAmount;
                    Integer unusedAmount = amount - usedAmount;

                    serviceGiftDTO.setAmount(amount);
                    serviceGiftDTO.setUsedAmount(usedAmount);
                    serviceGiftDTO.setUnusedAmount(unusedAmount);
                    serviceGiftMapper.updateGiftDTO(serviceGiftDTO);
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
