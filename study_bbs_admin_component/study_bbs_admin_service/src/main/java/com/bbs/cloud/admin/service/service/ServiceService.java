package com.bbs.cloud.admin.service.service;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.enums.gift.GiftEnum;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import com.bbs.cloud.admin.service.dto.ServiceGoldDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.mapper.ServiceGoldMapper;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import com.bbs.cloud.admin.service.result.ServiceInfoResult;
import com.bbs.cloud.admin.service.result.vo.GiftVO;
import com.bbs.cloud.admin.service.result.vo.GoldVO;
import com.bbs.cloud.admin.service.result.vo.ServiceGiftVO;
import com.bbs.cloud.admin.service.result.vo.ServiceGoldVO;
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

    @Autowired
    private ServiceGoldMapper serviceGoldMapper;

    public HttpResult sendMessage(OrderMessageParam param) {

        logger.info("进入接收订单消息接口，请求参数{}", JsonUtils.objectToJson(param));
        param.setDate(new Date());

        rabbitTemplate.convertAndSend(RabbitContant.SERVICE_EXCHANGE_NAME, RabbitContant.SERVICE_ROUTING_KEY, JsonUtils.objectToJson(param));

        return HttpResult.ok();
    }


    public HttpResult<ServiceInfoResult> queryService() {
        logger.info("查询服务信息");
        ServiceInfoResult serviceInfoResult = new ServiceInfoResult();
        serviceInfoResult.setServiceType(ServiceTypeEnum.getServiceTypeMap());

        logger.info("查询服务信息------获取礼物服务信息");
        ServiceGiftVO serviceGiftVO = new ServiceGiftVO();
        serviceGiftVO.setServiceType(ServiceTypeEnum.GIFT_MESSAGE.getType());
        List<ServiceGiftDTO> serviceGiftDTOS = serviceGiftMapper.queryGiftDTOList();
        List<GiftVO> giftVOS = new ArrayList<>();
        serviceGiftDTOS.forEach(item -> {
            GiftVO giftVO = new GiftVO();
            BeanUtils.copyProperties(item, giftVO);
            giftVOS.add(giftVO);
        });
        serviceGiftVO.setGiftList(giftVOS);
        serviceGiftVO.setGiftDescMap(GiftEnum.getGiftsJsonMap());
        serviceInfoResult.setServiceGiftVO(serviceGiftVO);

        logger.info("查询服务信息------获取金币服务信息");
        ServiceGoldVO serviceGoldVO = new ServiceGoldVO();
        serviceGoldVO.setServiceType(ServiceTypeEnum.RECHARGE_MESSAGE.getType());
        ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
        GoldVO goldVO = new GoldVO();
        BeanUtils.copyProperties(serviceGoldDTO, goldVO);
        serviceGoldVO.setGoldVO(goldVO);
        serviceInfoResult.setServiceGoldVO(serviceGoldVO);



        return new HttpResult<>(serviceInfoResult);
    }


    public HttpResult<Integer> queryServiceGiftTotal() {
        logger.info("远程调用 ----- Start ----- 获取服务组件的礼物总数量");
        Integer total = serviceGiftMapper.queryGiftAmount();
        if (total == null) {
            total = 0;
        }
        logger.info("远程调用 ----- 获取到服务组件的礼物总数量, total:{}", total);
        return new HttpResult<>(total);

    }

    public HttpResult<String> queryServiceGiftList() {
        logger.info("远程调用 ----- Start ----- 获取服务组件的礼物列表");
        List<ServiceGiftDTO> serviceGiftDTOS = serviceGiftMapper.queryGiftDTOList();
        logger.info("远程调用 ----- 获取到服务组件的礼物列表, serviceGiftDTOS:{}", JsonUtils.objectToJson(serviceGiftDTOS));
        List<GiftVO> giftVOS = new ArrayList<>();
        serviceGiftDTOS.forEach(item -> {
            GiftVO giftVO = new GiftVO();
            BeanUtils.copyProperties(item, giftVO);
            giftVOS.add(giftVO);
        });
        return new HttpResult(JsonUtils.objectToJson(giftVOS));
    }

    public HttpResult updateServiceGiftList(String data) {
        try {
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表, data：{}", data);
            List<ServiceGiftDTO> updateServiceGiftDTOS = JsonUtils.jsonToList(data, ServiceGiftDTO.class);
            serviceGiftMapper.updateGiftDTOList(updateServiceGiftDTOS);
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表成功, data：{}", data);
        } catch (Exception e) {
            logger.info("远程调用 ----- Start ----- 更新服务组件的礼物列表发生异常, data：{}", data);
            e.printStackTrace();
            return HttpResult.fail();
        }
        return HttpResult.ok();
    }

    public HttpResult<Integer> queryServiceGold() {
        logger.info("远程调用 ----- Start ----- 获取服务组件未使用的金币额度");
        ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
        if (serviceGoldDTO == null) {
            return new HttpResult<>(ServiceContant.DEFAULT_SERVICE_INITIAL_GOLD);
        }
        logger.info("远程调用 ----- Start ----- 获取服务组件未使用的金币额度,serviceGoldDTO:{}", JsonUtils.objectToJson(serviceGoldDTO));
        return new HttpResult<>(serviceGoldDTO.getUnusedGold());
    }

    public HttpResult updateServiceGold(Integer usedGold) {

        logger.info("远程调用 ----- Start ----- 更新服务组件的已使用的金币额度, usedGold：{}", usedGold);
        ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
        serviceGoldDTO.setUsedGold(serviceGoldDTO.getUsedGold() + usedGold);
        serviceGoldDTO.setUnusedGold(serviceGoldDTO.getUnusedGold() - usedGold);
        serviceGoldMapper.updateServiceGold(serviceGoldDTO);
        if (serviceGoldDTO.getUnusedGold() +serviceGoldDTO.getUsedGold() !=serviceGoldDTO.getGold()) {
           //TODO bug logger.info("金币数量异常");
        }
        logger.info("远程调用 -----更新服务组件的已使用的金币额度, usedGold：{}", usedGold);
        return HttpResult.ok();
    }

}