package com.bbs.cloud.admin.common.enums.activity;

import java.util.HashMap;
import java.util.Map;

public enum ActivityGoldStatusEnum {

    NORMAL(0, "NORMAL", "正常"),

    DEL(1, "DEL", "删除"),
    ;

    private Integer status;

    private String name;

    private String desc;

    private ActivityGoldStatusEnum(Integer status, String name, String desc) {
        this.status = status;
        this.name = name;
        this.desc = desc;
    }

    private static Map<Integer, String> activityGoldStatusMap = new HashMap<>();

    static {
        for (ActivityStatusEnum temp : ActivityStatusEnum.values()) {
            activityGoldStatusMap.put(temp.getStatus(), temp.getDesc());
        }
    }

    public static Map<Integer, String> getActivityGoldStatusMap() {
        return activityGoldStatusMap;
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
