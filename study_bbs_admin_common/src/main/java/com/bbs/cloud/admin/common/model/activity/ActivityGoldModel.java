package com.bbs.cloud.admin.common.model.activity;

/**
 * 活动金币
 */
public class ActivityGoldModel {

    private String id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 金币总额度
     */
    private Integer quota;

    /**
     * 未使用金币额度
     */
    private Integer unusedQuota;

    /**
     * 已使用金币额度
     */
    private Integer usedQuota;

    /**
     * 状态
     */
    private Integer status;

}
