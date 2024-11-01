package com.bbs.cloud.admin.activity.dto;

/**
 * 活动金币
 */
public class ActivityGoldDTO {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getUnusedQuota() {
        return unusedQuota;
    }

    public void setUnusedQuota(Integer unusedQuota) {
        this.unusedQuota = unusedQuota;
    }

    public Integer getUsedQuota() {
        return usedQuota;
    }

    public void setUsedQuota(Integer usedQuota) {
        this.usedQuota = usedQuota;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
