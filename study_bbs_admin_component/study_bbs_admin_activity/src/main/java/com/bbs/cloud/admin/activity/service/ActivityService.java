package com.bbs.cloud.admin.activity.service;

import com.bbs.cloud.admin.activity.contant.ActivityContant;
import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.mapper.LuckyBagMapper;
import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.param.OperatorActivityParam;
import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum;
import com.bbs.cloud.admin.common.enums.activity.LuckyBagStatusEnum;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ActivityService {

    final static Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private List<ActivityManage> activityManages;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private LuckyBagMapper luckyBagMapper;

    public HttpResult createActivity(CreateActivityParam param) {

        logger.info("开始创建活动，请求参数：{}", JsonUtils.objectToJson(param));

        String name = param.getName();
        String content = param.getContent();
        Integer activityType = param.getActivityType();

        if (StringUtils.isEmpty(name)) {
            logger.info("开始创建活动,活动名字为空, 请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NAME_IS_NOT_EMTRY);
        }

        if (StringUtils.isEmpty(content)) {
            logger.info("开始创建活动,活动内容为空, 请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_CONTENT_IS_NOT_EMTRY);
        }

        if (ActivityTypeEnum.getActivityTypeEnumMap().getOrDefault(activityType, null) == null) {
            logger.info("开始创建活动,活动类型不存在, 请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_CONTENT_IS_NOT_EMTRY);
        }

        ActivityDTO activityDTO = activityMapper.queryActivityByType(activityType, Arrays.asList
                (ActivityStatusEnum.INITIAL.getStatus(), ActivityStatusEnum.RUNNING.getStatus()));

        if (activityDTO != null){
            logger.info("开始创建活动,活动类型已存在, 请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_TYPE_ENTITY_IS_EXIST);
        }

        return activityManages.stream()
                .filter(item -> item.getActivityType().equals(activityType))
                .findFirst().get()
                .createActivity(param);
    }

    public HttpResult startActivity(OperatorActivityParam param) {
        logger.info("启动活动, 请求参数：{}", JsonUtils.objectToJson(param));
        String id = param.getId();
        if (StringUtils.isEmpty(id)){
            logger.info("启动活动,活动ID为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_IS_NOT_NULL);
        }
        if (ActivityContant.ACTIVITY_ID_LENGTH != id.length()){
            logger.info("启动活动,活动ID格式不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_FORMAT_NOT_TRUE);
        }
        ActivityDTO activityDTO = activityMapper.queryActivityById(id);
        if (activityDTO == null){
            logger.info("启动活动,活动不存在,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_IS_NOT_EXIST);
        }
        if(!activityDTO.getStatus().equals(ActivityStatusEnum.INITIAL.getStatus())){
            logger.info("启动活动,活动状态不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_NOT_TRUE);
        }
        Integer activityType = activityDTO.getActivityType();

        return activityManages.stream()
                .filter(item -> item.getActivityType().equals(activityType))
                .findFirst().get()
                .startActivity(activityDTO);
    }

    public HttpResult endActivity(OperatorActivityParam param)  {
        logger.info("终止活动, 请求参数：{}", JsonUtils.objectToJson(param));
        String id = param.getId();
        if (StringUtils.isEmpty(id)){
            logger.info("终止活动,活动ID为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_IS_NOT_NULL);
        }
        if (ActivityContant.ACTIVITY_ID_LENGTH != id.length()){
            logger.info("终止活动,活动ID格式不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_FORMAT_NOT_TRUE);
        }
        ActivityDTO activityDTO = activityMapper.queryActivityById(id);
        if (activityDTO == null){
            logger.info("终止活动,活动不存在,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_IS_NOT_EXIST);
        }
        if(!activityDTO.getStatus().equals(ActivityStatusEnum.RUNNING.getStatus())){
            logger.info("终止活动,活动状态不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_NOT_TRUE);
        }
        Integer activityType = activityDTO.getActivityType();

        return activityManages.stream()
                .filter(item -> item.getActivityType().equals(activityType))
                .findFirst().get()
                .endActivity(activityDTO);
    }

    public HttpResult<Integer> queryGiftTotalByType(Integer giftType) {
        logger.info("远程调用----start------获取待领取和已经被领取的礼物数量,giftType:{}", giftType);
        Integer amount = luckyBagMapper.queryGiftAmount(giftType, Arrays.asList(LuckyBagStatusEnum.NORMAL.getStatus(), LuckyBagStatusEnum.GETED.getStatus()));
        //初始表无数据时 手动设0
        if (amount == null){
            amount = 0;
        }
        logger.info("远程调用----------获取待领取和已经被领取的礼物数量,giftType:{},amount:{}", giftType,amount);
        return new HttpResult(amount);
    }
}
