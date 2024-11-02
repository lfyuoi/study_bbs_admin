package com.bbs.cloud.admin.activity.param;

public class CreateActivityParam {

    private String name;

    private String content;

    /**
     * {@link com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum}
     */
    private Integer activityType;

    private Integer amount;

    private Integer quota;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }
}
