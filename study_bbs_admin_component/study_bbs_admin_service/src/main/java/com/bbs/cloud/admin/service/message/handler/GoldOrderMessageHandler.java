package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGoldDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGoldMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoldOrderMessageHandler implements MessageHandler {

    final static  org.slf4j.Logger logger = LoggerFactory.getLogger(GoldOrderMessageHandler.class);

    @Autowired
    private ServiceGoldMapper serviceGoldMapper;

    @Override
    public void handler(OrderMessageDto orderMessageDto) {
        logger.info("开始处理充值服务订单,message={}", JsonUtils.objectToJson(orderMessageDto));
        try {
            ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
            if (serviceGoldDTO == null){
                serviceGoldDTO = new ServiceGoldDTO();
                serviceGoldDTO.setId(CommonUtil.createUUID());
                serviceGoldDTO.setName(ServiceContant.SERVICE_GOLD_NAME);
                serviceGoldDTO.setGold(ServiceContant.DEFAULT_SERVICE_GOLD);
                serviceGoldDTO.setUsedGold(ServiceContant.DEFAULT_SERVICE_USED_GIFT_AMOUNT);
                serviceGoldDTO.setUnusedGold(ServiceContant.DEFAULT_SERVICE_UNUSED_GOLD);
                serviceGoldMapper.insertServiceGold(serviceGoldDTO);
            }else {
                /**
                 * TODO:去管理过去活动中金币的使用情况，进行库存更新
                 */
            }

        }catch (Exception e){
            logger.error("开始处理充值服务订单,发生异常,message={}", JsonUtils.objectToJson(orderMessageDto));
            e.printStackTrace();
        }
    }

    @Override
    public Integer getServiceType() {
        return ServiceTypeEnum.RECHARGE_MESSAGE.getType();
    }
}
