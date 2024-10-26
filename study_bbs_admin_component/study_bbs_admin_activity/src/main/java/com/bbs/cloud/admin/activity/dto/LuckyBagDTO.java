package com.bbs.cloud.admin.activity.dto;

/**
 * 福袋----可以获取礼物
 */
public class LuckyBagDTO {

    private String id;

    private String activityId;

    private Integer status;

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 福袋中携带的礼物
     */
    private Integer giftType;

}
