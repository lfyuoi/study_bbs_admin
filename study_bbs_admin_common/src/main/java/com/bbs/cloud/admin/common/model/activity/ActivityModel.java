package com.bbs.cloud.admin.common.model.activity;

import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum;

import java.util.Date;

public class ActivityModel {

    private String id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 活动状态
     * {@link ActivityStatusEnum}
     */
    private Integer status;

    /**
     * 活动类型
     * {@link ActivityTypeEnum}
     */
    private Integer activityType;

    /**
     * 福袋/礼物/红包的数量
     */
    private Integer amount;

    /**
     * 红包总额度
     */
    private Integer quota;

    /**
     * 活动背景地址
     */
    private String imgLink;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 活动开始时间
     */
    private Date startDate;

    /**
     * 活动终止时间
     */
    private Date endDate;

}
