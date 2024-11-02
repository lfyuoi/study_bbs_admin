package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.contant.RedisContant;
import com.bbs.cloud.admin.common.error.CommonExceptionEnum;
import com.bbs.cloud.admin.common.feigh.client.ActivityFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.common.util.RedisLockHelper;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGoldDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGoldMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoldOrderMessageHandler implements MessageHandler {

    final static Logger logger = LoggerFactory.getLogger(GoldOrderMessageHandler.class);

    @Autowired
    private ServiceGoldMapper serviceGoldMapper;

    @Autowired
    private ActivityFeighClient activityFeighClient;

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void handler(OrderMessageDto orderMessageDto) {
        logger.info("开始处理充值服务订单,message={}", JsonUtils.objectToJson(orderMessageDto));

        String key = RedisContant.BBS_CLOUD_LOCK_GOLD_KEY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 10000L)) {
                ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
                if (serviceGoldDTO == null) {
                    serviceGoldDTO = new ServiceGoldDTO();
                    serviceGoldDTO.setId(CommonUtil.createUUID());
                    serviceGoldDTO.setName(ServiceContant.SERVICE_GOLD_NAME);
                    serviceGoldDTO.setGold(ServiceContant.DEFAULT_SERVICE_GOLD);
                    serviceGoldDTO.setUsedGold(ServiceContant.DEFAULT_SERVICE_USED_GIFT_AMOUNT);
                    serviceGoldDTO.setUnusedGold(ServiceContant.DEFAULT_SERVICE_UNUSED_GOLD);
                    serviceGoldMapper.insertServiceGold(serviceGoldDTO);
                } else {
                    logger.info("开始处理金币服务订单-整理库存,message={}", JsonUtils.objectToJson(serviceGoldDTO));
                    HttpResult<Integer> result = activityFeighClient.queryUsedGold();
                    logger.info("开始处理金币服务订单-整理库存--远程调用---获取活动中待使用和已使用的金币总额,result={}", JsonUtils.objectToJson(result));
                    if (result == null || !result.getCode().equals(CommonExceptionEnum.SUCCESS.getCode()) || result.getData() == null) {
                        logger.error("开始处理金币服务订单-整理库存--远程调用---获取活动中待使用和已使用的金币总额发生异常,result={}", JsonUtils.objectToJson(result));
                        result.setCode(0);
                    }
                    Integer usedGold = result.getData();
                    serviceGoldDTO.setUsedGold(usedGold);
                    serviceGoldDTO.setUnusedGold(serviceGoldDTO.getGold() - serviceGoldDTO.getUsedGold());
                    serviceGoldDTO.setGold(serviceGoldDTO.getGold() + ServiceContant.DEFAULT_SERVICE_GOLD);
                    serviceGoldMapper.updateServiceGold(serviceGoldDTO);

                    /**
                     * TODO:去管理过去活动中金币的使用情况，进行库存更新
                     */
                }
            }

        } catch (Exception e) {
            logger.error("开始处理充值服务订单,发生异常,message={}", JsonUtils.objectToJson(orderMessageDto));
            e.printStackTrace();
        } finally {
            redisLockHelper.releaseLock(key);
        }
    }

    @Override
    public Integer getServiceType() {
        return ServiceTypeEnum.RECHARGE_MESSAGE.getType();
    }
}
