package com.bbs.cloud.admin.activity.service;

import com.bbs.cloud.admin.activity.contant.ActivityContant;
import com.bbs.cloud.admin.activity.dto.ActivityConditionDTO;
import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.dto.ActivityGoldDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityGoldMapper;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.mapper.LuckyBagMapper;
import com.bbs.cloud.admin.activity.mapper.RedPacketMapper;
import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.param.OperatorActivityParam;
import com.bbs.cloud.admin.activity.param.QueryActivityPageByConditionParam;
import com.bbs.cloud.admin.activity.result.ActivityPageResult;
import com.bbs.cloud.admin.activity.result.vo.ActivityVO;
import com.bbs.cloud.admin.common.enums.activity.*;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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

    @Autowired
    private RedPacketMapper redPacketMapper;

    @Autowired
    private ActivityGoldMapper activityGoldMapper;


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

        if (activityDTO != null) {
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
        if (StringUtils.isEmpty(id)) {
            logger.info("启动活动,活动ID为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_IS_NOT_NULL);
        }
        if (ActivityContant.ACTIVITY_ID_LENGTH != id.length()) {
            logger.info("启动活动,活动ID格式不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_FORMAT_NOT_TRUE);
        }
        ActivityDTO activityDTO = activityMapper.queryActivityById(id);
        if (activityDTO == null) {
            logger.info("启动活动,活动不存在,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_IS_NOT_EXIST);
        }
        if (!activityDTO.getStatus().equals(ActivityStatusEnum.INITIAL.getStatus())) {
            logger.info("启动活动,活动状态不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_NOT_TRUE);
        }
        Integer activityType = activityDTO.getActivityType();

        return activityManages.stream()
                .filter(item -> item.getActivityType().equals(activityType))
                .findFirst().get()
                .startActivity(activityDTO);
    }

    public HttpResult endActivity(OperatorActivityParam param) {
        logger.info("终止活动, 请求参数：{}", JsonUtils.objectToJson(param));
        String id = param.getId();
        if (StringUtils.isEmpty(id)) {
            logger.info("终止活动,活动ID为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_IS_NOT_NULL);
        }
        if (ActivityContant.ACTIVITY_ID_LENGTH != id.length()) {
            logger.info("终止活动,活动ID格式不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_ID_FORMAT_NOT_TRUE);
        }
        ActivityDTO activityDTO = activityMapper.queryActivityById(id);
        if (activityDTO == null) {
            logger.info("终止活动,活动不存在,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_IS_NOT_EXIST);
        }
        if (!activityDTO.getStatus().equals(ActivityStatusEnum.RUNNING.getStatus())) {
            logger.info("终止活动,活动状态不正确,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_NOT_TRUE);
        }
        Integer activityType = activityDTO.getActivityType();

        return activityManages.stream()
                .filter(item -> item.getActivityType().equals(activityType))
                .findFirst().get()
                .endActivity(activityDTO);
    }

    public HttpResult queryActivityPageByCondition(QueryActivityPageByConditionParam param) {
        logger.info("按照条件分页查询活动列表, param：{}", JsonUtils.objectToJson(param));
        Integer pageNow = param.getPageNow();
        if (ObjectUtils.isEmpty(pageNow)) {
            logger.info("按照条件分页查询活动列表, 当前页码为空, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.PAGE_NOW_IS_NOT_NULL);
        }
        if (pageNow < ActivityContant.DEFAULT_MIN_PAGENOW) {
            logger.info("按照条件分页查询活动列表, 当前页码小于1, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.PAGE_NOW_LESS_THAN_ZERO);
        }
        Integer pageSize = param.getPageSize();
        if (ObjectUtils.isEmpty(pageSize)) {
            logger.info("按照条件分页查询活动列表, 当前数据量为空, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.PAGE_SIZE_IS_NOT_NULL);
        }
        if (pageSize <= ActivityContant.DEFAULT_MIN_PAGESIZE) {
            logger.info("按照条件分页查询活动列表, 当前数据量小于1, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.PAGE_SIZE_LESS_THAN_ZERO);
        }
        Integer type = param.getType();
        if (ObjectUtils.isEmpty(type)) {
            logger.info("按照条件分页查询活动列表, 活动类型为空, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_TYPE_IS_NOT_NULL);
        }
        if (ActivityTypeEnum.getActivityQueryUsedMap().getOrDefault(type, null) == null) {
            logger.info("按照条件分页查询活动列表, 活动类型不正确, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_TYPE_IS_NOT_EXIST);
        }
        Integer status = param.getStatus();
        if (ObjectUtils.isEmpty(status)) {
            logger.info("按照条件分页查询活动列表, 活动状态为空, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_IS_NOT_NULL);
        }
        if (ActivityStatusEnum.getActivityStatusQueryMap().getOrDefault(status, null) == null) {
            logger.info("按照条件分页查询活动列表, 活动状态不正确, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_STATUS_NOT_TRUE);
        }

        List<Integer> typeList = new ArrayList<>();
        if (type.equals(ActivityTypeEnum.ALL.getType())) {
            typeList.add(ActivityTypeEnum.LUCKY_BAG.getType());
            typeList.add(ActivityTypeEnum.RED_PACKET.getType());
            typeList.add(ActivityTypeEnum.SCORE_EXCHANGE_LUCKY_BAG.getType());
            typeList.add(ActivityTypeEnum.SCORE_EXCHANGE_GOLD.getType());
        } else {
            typeList.add(type);
        }

        List<Integer> statusList = new ArrayList<>();
        if (status.equals(ActivityStatusEnum.ALL.getStatus())) {
            statusList.add(ActivityStatusEnum.INITIAL.getStatus());
            statusList.add(ActivityStatusEnum.RUNNING.getStatus());
            statusList.add(ActivityStatusEnum.END.getStatus());
        } else {
            statusList.add(status);
        }

        Integer start = (pageNow -1) * pageSize;
        Integer limit = pageSize;

        ActivityConditionDTO activityConditionDTO = new ActivityConditionDTO();
        activityConditionDTO.setStatusList(statusList);
        activityConditionDTO.setTypeList(typeList);
        activityConditionDTO.setStart(start);
        activityConditionDTO.setLimit(limit);

        Integer total = activityMapper.queryActivityCountByCondition(activityConditionDTO);
        if (ObjectUtils.isEmpty(total)) {
            logger.info("按照条件分页查询活动列表, 当前条件没有查到数据, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_DATA_IS_NULL);
        }
        Integer pageTotal = total / pageSize + 1;
        if (pageNow > pageTotal) {
            logger.info("按照条件分页查询活动列表, 当前页大于总页数, param：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.PAGE_NOW_GREATER_TOTAL_PAGE);
        }
        List<ActivityDTO> activityDTOS = activityMapper.queryActivityByCondition(activityConditionDTO);

        List<ActivityVO> activityVOS = new ArrayList<>();
        activityDTOS.forEach(item -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(item, activityVO);
            activityVOS.add(activityVO);
        });

        ActivityPageResult result = new ActivityPageResult();
        result.setStatusMap(ActivityStatusEnum.getActivityStatusMap());
        result.setTypeMap(ActivityTypeEnum.getActivityTypeMap());
        result.setData(activityVOS);

        return new HttpResult(result);

    }


    public HttpResult<Integer> queryGiftTotalByType(Integer giftType) {
        logger.info("远程调用----start------获取待领取和已经被领取的礼物数量,giftType:{}", giftType);
        Integer amount = luckyBagMapper.queryGiftAmount(giftType, Arrays.asList(LuckyBagStatusEnum.NORMAL.getStatus(), LuckyBagStatusEnum.GETED.getStatus()));
        //初始表无数据时 手动设0
        if (amount == null) {
            amount = 0;
        }
        logger.info("远程调用----------获取待领取和已经被领取的礼物数量,giftType:{},amount:{}", giftType, amount);
        return new HttpResult(amount);
    }

    public HttpResult<Integer> queryUsedGold() {
        logger.info("远程调用----start------获取待领取和已经被领取的红包金币的总额");
        Integer total = 0;

        Integer normalRedPacketGoldTotal = redPacketMapper.queryActivityRedPacket(RedPacketStatusEnum.NORMAL.getStatus());
        if (normalRedPacketGoldTotal == null) {
            normalRedPacketGoldTotal = 0;
        }

        logger.info("远程调用----start------获取待领取的红包金币的总额,normalRedPacketGoldTotal:{}", normalRedPacketGoldTotal);

        total = total + normalRedPacketGoldTotal;
        Integer getedRedPacketGoldTotal = redPacketMapper.queryActivityRedPacket(RedPacketStatusEnum.GETED.getStatus());

        if (getedRedPacketGoldTotal == null) {
            getedRedPacketGoldTotal = 0;
        }

        logger.info("远程调用----start------获取被领取的红包金币的总额,getedRedPacketGoldTotal:{}", getedRedPacketGoldTotal);
        total = total + getedRedPacketGoldTotal;

        Integer endActivityUsedGold = activityGoldMapper.queryUsedAmountTotalByStatus(ActivityGoldStatusEnum.DEL.getStatus());
        if (endActivityUsedGold == null) {
            endActivityUsedGold = 0;
        }
        logger.info("远程调用---------获取被终止的活动使用了多少金币,endActivityUsedGold:{}", endActivityUsedGold);
        total = total + endActivityUsedGold;

        ActivityGoldDTO activityGoldDTO = activityGoldMapper.queryActivityGoldDTOByStatus(ActivityGoldStatusEnum.NORMAL.getStatus());
        logger.info("远程调用---------获取初始化或者运行中的活动使用了多少金币,activityGoldDTO:{}", activityGoldDTO);
        if (activityGoldDTO != null) {
            total = total + activityGoldDTO.getQuota();
        }

        return new HttpResult<>(total);
    }

    public List<ActivityDTO> queryActivityList() {
        return activityMapper.queryActivityList();
    }


}
