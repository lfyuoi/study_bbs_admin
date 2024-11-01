package com.bbs.cloud.admin.activity.service.manage;

import com.bbs.cloud.admin.activity.contant.ActivityContant;
import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.dto.ActivityGoldDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityGoldMapper;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.service.ActivityManage;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.contant.RedisContant;
import com.bbs.cloud.admin.common.enums.activity.ActivityGoldStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum;
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

import java.util.Date;

@Service
public class ScoreExchangeGoldActivityManage implements ActivityManage {

    final static Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ServiceFeighClient serviceFeighClient;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private ActivityGoldMapper activityGoldMapper;

    @Override
    @Transactional(rollbackFor = {HttpException.class, Exception.class})
    public HttpResult createActivity(CreateActivityParam param) {
        logger.info("开始创建积分兑换金币活动,请求参数：{}", JsonUtils.objectToJson(param));

        Integer quota = param.getQuota();
        if (ObjectUtils.isEmpty(quota)) {
            logger.info("开始创建积分兑换金币活动,金币额度不能为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.SCORE_GOLD_ACTIVITY_QUOTA_IS_NOT_NULL);
        }
        if (quota < ActivityContant.DEFAULT_RED_PACKET_ACTIVITY_MIN_QUOTA) {
            logger.info("开始创建积分兑换金币活动,金币额度不能小于1，请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.SCORE_GOLD_ACTIVITY_QUOTA_LESS_THAN_ONE);
        }
        String key = RedisContant.BBS_CLOUD_ACTIVITY_SCORE_GOLD;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                logger.info("远程调用------start------获取服务组件未使用的金币额度");
                HttpResult<Integer> result = serviceFeighClient.queryServiceGold();
                logger.info("远程调用------start------获取服务组件未使用的金币额度,result:{}", JsonUtils.objectToJson(result));
                if (result == null || !CommonExceptionEnum.SUCCESS.getCode().equals(result.getCode()) || result.getData() == null) {
                    logger.info("远程调用------start------获取系统服务未使用金币总数量异常,result:{}", JsonUtils.objectToJson(result));
                    return HttpResult.generateHttpResult(ActivityException.SCORE_GOLD_ACTIVITY_SERVICE_GOLD_AMOUNT_QUERY_FAIL);
                }
                Integer serviceGold = result.getData();
                if (serviceGold < quota) {
                    logger.info("开始创建积分兑换金币活动,系统服务未使用金币总数量不足，请求参数：{},serviceGold:{}", JsonUtils.objectToJson(param), serviceGold);
                    return HttpResult.generateHttpResult(ActivityException.SCORE_GOLD_ACTIVITY_SERVICE_GOLD_AMOUNT_NOT_MEET);
                }
                logger.info("开始创建积分兑换金币活动---开始创建活动,请求参数：{} ", JsonUtils.objectToJson(param));
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
                logger.info("开始创建积分兑换金币活动---开始封装金币使用记录,请求参数：{} ", JsonUtils.objectToJson(param));
                ActivityGoldDTO activityGoldDTO = new ActivityGoldDTO();
                activityGoldDTO.setActivityId(activityDTO.getId());
                activityGoldDTO.setId(CommonUtil.createUUID());
                activityGoldDTO.setStatus(ActivityGoldStatusEnum.NORMAL.getStatus());
                activityGoldDTO.setQuota(quota);
                activityGoldDTO.setUsedQuota(ActivityContant.DEFAULT_ACTIVITY_GOLD_USED_AMOUNT);
                activityGoldDTO.setUnusedQuota(quota);
                activityGoldMapper.insertActivityGoldDTO(activityGoldDTO);

                logger.info("开始创建积分兑换金币活动---开始更新服务组件被使用的金币数量,请求参数：{} ", JsonUtils.objectToJson(param));
                HttpResult updateResult = serviceFeighClient.updateServiceGold(quota);
                if (updateResult == null || !CommonExceptionEnum.SUCCESS.getCode().equals(updateResult.getCode())){
                    throw new HttpException(ActivityException.SCORE_GOLD_ACTIVITY_SERVICE_GOLD_AMOUNT_UPDATE_FAIL);
                }
            } else {
                logger.info("开始创建积分兑换金币活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }
        } catch (HttpException e) {
            logger.info("开始创建积分兑换金币活动,发生HttpException异常,请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.info("开始创建积分兑换金币活动,Exception,请求参数：{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        } finally {
            redisLockHelper.releaseLock(key);
        }
        return HttpResult.ok();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public HttpResult startActivity(ActivityDTO activityDTO) {
        logger.info("启动积分兑换金币活动,请求参数：{}", JsonUtils.objectToJson(activityDTO));
        String key = RedisContant.BBS_CLOUD_LOCK_ACTIVITY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                activityDTO.setStatus(ActivityStatusEnum.RUNNING.getStatus());
                activityDTO.setStartDate(new Date());
                activityDTO.setUpdateDate(new Date());
                activityMapper.updateActivity(activityDTO);

                ActivityGoldDTO activityGoldDTO = activityGoldMapper.queryActivityGoldDTOByActivityId(activityDTO.getId());

                jedisUtil.set(RedisContant.BBS_CLOUD_ACTIVITY_SCORE_GOLD, String.valueOf(activityGoldDTO.getUnusedQuota()));
            } else {
                logger.info("启动积分兑换金币活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(activityDTO));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }

        } catch (Exception e) {
            logger.info("启动积分兑换金币活动,发生异常,请求参数：{}", JsonUtils.objectToJson(activityDTO));
            jedisUtil.del(RedisContant.BBS_CLOUD_ACTIVITY_SCORE_GOLD);
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
        logger.info("终止积分兑换金币活动,请求参数：{}", JsonUtils.objectToJson(activityDTO));
        String key = RedisContant.BBS_CLOUD_LOCK_ACTIVITY;
        try {
            if (redisLockHelper.lock(key, CommonUtil.createUUID(), 3000L)) {
                activityDTO.setStatus(ActivityStatusEnum.END.getStatus());
                activityDTO.setEndDate(new Date());
                activityDTO.setUpdateDate(new Date());
                activityMapper.updateActivity(activityDTO);

                ActivityGoldDTO activityGoldDTO = activityGoldMapper.queryActivityGoldDTOByActivityId(activityDTO.getId());
                Integer unusedGold = jedisUtil.get(RedisContant.BBS_CLOUD_ACTIVITY_SCORE_GOLD, Integer.class);
                jedisUtil.del(RedisContant.BBS_CLOUD_ACTIVITY_SCORE_GOLD);
                activityGoldDTO.setUnusedQuota(unusedGold);
                activityGoldDTO.setUsedQuota(activityGoldDTO.getQuota() - activityGoldDTO.getUnusedQuota());
                activityGoldDTO.setStatus(ActivityGoldStatusEnum.DEL.getStatus());
                activityGoldMapper.updateActivityGoldDTO(activityGoldDTO);


            } else {
                logger.info("终止积分兑换金币活动,请勿重复操作,请求参数：{}", JsonUtils.objectToJson(activityDTO));
                return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NOT_REPEAT_MANAGE);
            }

        } catch (Exception e) {
            logger.info("终止积分兑换金币活动,发生异常,请求参数：{}", JsonUtils.objectToJson(activityDTO));
            e.printStackTrace();
            throw e;
        } finally {
            redisLockHelper.releaseLock(key);
        }
        return HttpResult.ok();
    }

    @Override
    public Integer getActivityType() {
        return ActivityTypeEnum.SCORE_EXCHANGE_GOLD.getType();
    }
}
