package com.bbs.cloud.admin.activity.dto;

/**
 * 红包
 */
public class RedPacketDTO {

    private String id;

    private String activityId;

    /**
     * 红包状态
     */
    private Integer status;

    /**
     * 红包额度
     */
    private Integer gold;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

}
