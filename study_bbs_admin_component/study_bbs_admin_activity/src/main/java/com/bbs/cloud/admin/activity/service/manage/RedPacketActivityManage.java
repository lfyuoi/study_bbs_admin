package com.bbs.cloud.admin.activity.service.manage;

import com.bbs.cloud.admin.activity.contant.ActivityContant;
import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.dto.RedPacketDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.mapper.RedPacketMapper;
import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.service.ActivityManage;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.contant.RedisContant;
import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum;
import com.bbs.cloud.admin.common.enums.activity.LuckyBagStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.RedPacketStatusEnum;
import com.bbs.cloud.admin.common.error.CommonExceptionEnum;
import com.bbs.cloud.admin.common.error.HttpException;
import com.bbs.cloud.admin.common.feigh.client.ServiceFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JedisUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.common.util.RedisLockHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RedPacketActivityManage implements ActivityManage {

    final static Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ServiceFeighClient serviceFeighClient;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RedPacketMapper redPacketMapper;

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    @Transactional(rollbackFor = {HttpException.class, Exception.class})
    public HttpResult createActivity(CreateActivityParam param) {
        logger.info("开始创建红包活动,请求参数：{}", JsonUtils.objectToJson(param));
        Integer amount = param.getAmount();
        if (ObjectUtils.isEmpty(amount)) {
            logger.info("开始创建红包活动,红包活动数量不能为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_AMOUNT_IS_NOT_NULL);
        }
        if (amount < ActivityContant.DEFAULT_RED_PACKET_ACTIVITY_MIN_AMOUNT) {
            logger.info("开始创建红包活动,红包活动数量不能小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_AMOUNT_LESS_THAN_ONE);
        }
        Integer quota = param.getQuota();
        if (ObjectUtils.isEmpty(quota)) {
            logger.info("开始创建红包活动,红包额度不能为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_QUOTA_IS_NOT_NULL);
        }
        if (quota < ActivityContant.DEFAULT_RED_PACKET_ACTIVITY_MIN_QUOTA) {
            logger.info("开始创建红包活动,红包额度不能小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_QUOTA_LESS_THAN_ONE);
        }
        String key = RedisContant.BBS_CLOUD_LOCK_GOLD_KEY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                logger.info("远程调用------start------获取服务组件未使用的金币额度");
                HttpResult<Integer> result = serviceFeighClient.queryServiceGold();
                logger.info("远程调用------start------获取服务组件未使用的金币额度,result:{}", JsonUtils.objectToJson(result));
                if (result == null || !CommonExceptionEnum.SUCCESS.getCode().equals(result.getCode()) || result.getData() == null) {
                    logger.info("远程调用------start------获取服务组件未使用的金币额度发生异常,result:{}", JsonUtils.objectToJson(result));
                    return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_SERVICE_GOLD_AMOUNT_QUERY_FAIL);
                }
                Integer serviceGold = result.getData();
                if (serviceGold < quota) {
                    logger.info("开始创建红包活动,服务组件金币额度不足，请求参数：{},serviceGold:{}", JsonUtils.objectToJson(param), serviceGold);
                    return HttpResult.generateHttpResult(ActivityException.RED_PACKET_ACTIVITY_SERVICE_GOLD_AMOUNT_NOT_MEET);
                }
                logger.info("开始创建红包活动---开始创建活动,请求参数：{} ", JsonUtils.objectToJson(param));
                //第一步：创建活动
                ActivityDTO activityDTO = new ActivityDTO();
                activityDTO.setId(CommonUtil.createUUID());
                activityDTO.setName(param.getName());
                activityDTO.setQuota(quota);
                activityDTO.setActivityType(param.getActivityType());
                activityDTO.setContent(param.getContent());
                activityDTO.setStatus(ActivityStatusEnum.INITIAL.getStatus());
                activityDTO.setCreateDate(new Date());
                activityDTO.setUpdateDate(new Date());
                activityMapper.insertActivityDTO(activityDTO);

                //第二步：封装红包
                logger.info("开始创建红包活动---开始封装红包,请求参数：{} ", JsonUtils.objectToJson(param));
                List<RedPacketDTO> redPacketDTOS = packRedPacket(amount, quota, activityDTO.getId());
                redPacketMapper.insertRedPacketList(redPacketDTOS);

                logger.info("开始创建红包活动---开始更新服务组件被使用的金币数量,请求参数：{} ", JsonUtils.objectToJson(param));
                HttpResult updateResult = serviceFeighClient.updateServiceGold(quota);
                if (updateResult == null || !CommonExceptionEnum.SUCCESS.getCode().equals(updateResult.getCode())){
                    throw new HttpException(ActivityException.RED_PACKET_ACTIVITY_SERVICE_GOLD_UPDATE_FAIL);
                }
            } else {
                logger.info("开始创建红包活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }
        } catch (HttpException e) {
            logger.info("开始创建红包活动,发生HttpException异常,请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.info("开始创建红包活动,Exception,请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        } finally {
            redisLockHelper.releaseLock(key);
        }
        return HttpResult.ok();
    }


    private List<RedPacketDTO> packRedPacket(Integer amount, Integer quota, String activityId) {
        int gold = quota / amount;
        List<RedPacketDTO> redPacketDTOS = new ArrayList<>();
        while (amount > 0) {
            RedPacketDTO redPacketDTO = new RedPacketDTO();
            redPacketDTO.setId(CommonUtil.createUUID());
            redPacketDTO.setGold(gold);
            redPacketDTO.setActivityId(activityId);
            redPacketDTO.setStatus(RedPacketStatusEnum.NORMAL.getStatus());
            redPacketDTOS.add(redPacketDTO);
            amount--;
        }
        return redPacketDTOS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public HttpResult startActivity(ActivityDTO activityDTO) {
        logger.info("启动红包活动,请求参数：{}", JsonUtils.objectToJson(activityDTO));
        String key = RedisContant.BBS_CLOUD_LOCK_ACTIVITY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                activityDTO.setStatus(ActivityStatusEnum.RUNNING.getStatus());
                activityDTO.setStartDate(new Date());
                activityDTO.setUpdateDate(new Date());
                activityMapper.updateActivity(activityDTO);

                List<RedPacketDTO> redPacketDTOSList = redPacketMapper.queryRedPacketList(activityDTO.getId());
                redPacketDTOSList.forEach(item -> {
                    jedisUtil.lpush(RedisContant.BBS_CLOUD_ACTIVITY_RED_PACKET_LIST, JsonUtils.objectToJson(item));
                });

            } else {
                logger.info("启动红包活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(activityDTO));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }

        } catch (Exception e) {
            logger.info("启动红包活动,发生异常,请求参数：{}", JsonUtils.objectToJson(activityDTO));
            jedisUtil.del(RedisContant.BBS_CLOUD_ACTIVITY_RED_PACKET_LIST);
            e.printStackTrace();
            throw e;
        } finally {
            redisLockHelper.releaseLock(key);
        }
        return HttpResult.ok();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public HttpResult endActivity(ActivityDTO activityDTO) {
        logger.info("终止红包活动,请求参数：{}", JsonUtils.objectToJson(activityDTO));
        String key = RedisContant.BBS_CLOUD_LOCK_ACTIVITY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                activityDTO.setStatus(ActivityStatusEnum.END.getStatus());
                activityDTO.setEndDate(new Date());
                activityDTO.setUpdateDate(new Date());
                activityMapper.updateActivity(activityDTO);
                jedisUtil.del(RedisContant.BBS_CLOUD_ACTIVITY_RED_PACKET_LIST);

                redPacketMapper.updateRedPacket(activityDTO.getId(), LuckyBagStatusEnum.NORMAL.getStatus(), LuckyBagStatusEnum.INVALID.getStatus());
            } else {
                logger.info("终止红包活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(activityDTO));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }

        } catch (Exception e) {
            logger.info("终止红包活动,发生异常,请求参数：{}", JsonUtils.objectToJson(activityDTO));
            e.printStackTrace();
            throw e;
        } finally {
            redisLockHelper.releaseLock(key);
        }
        return HttpResult.ok();
    }

    @Override
    public Integer getActivityType() {
        return ActivityTypeEnum.RED_PACKET.getType();
    }
}
