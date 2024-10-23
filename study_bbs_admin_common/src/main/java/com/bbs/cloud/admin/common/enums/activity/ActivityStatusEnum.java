package com.bbs.cloud.admin.common.enums.activity;

import java.util.HashMap;
import java.util.Map;

public enum ActivityStatusEnum {

    ALL(0, "ALL", "所有状态"),

    INITIAL(1, "INITIAL", "活动初始化"),

    RUNNING(2, "RUNNING", "活动进行中"),

    END(3, "END", "活动终止")

    ;

    private Integer status;

    private String name;

    private String desc;

    private ActivityStatusEnum(Integer status, String name, String desc) {
        this.status = status;
        this.name = name;
        this.desc = desc;
    }

    private static Map<Integer, ActivityStatusEnum> activityStatusQueryMap = new HashMap<>();

    private static Map<Integer, String> activityStatusMap = new HashMap<>();

    static {
        for(ActivityStatusEnum temp : ActivityStatusEnum.values()) {
            activityStatusQueryMap.put(temp.getStatus(), temp);
            activityStatusMap.put(temp.getStatus(), temp.getDesc());
        }
        activityStatusMap.remove(ALL.getStatus());
    }

    public static Map<Integer, String> getActivityStatusMap() {
        return activityStatusMap;
    }

    public static Map<Integer, ActivityStatusEnum> getActivityStatusQueryMap() {
        return activityStatusQueryMap;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
