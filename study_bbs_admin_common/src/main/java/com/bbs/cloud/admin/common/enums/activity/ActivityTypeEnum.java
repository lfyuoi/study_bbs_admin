package com.bbs.cloud.admin.common.enums.activity;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动类型枚举
 */
public enum ActivityTypeEnum {

    //分页查询时使用
    ALL(0, "ALL", "所有活动"),

    LUCKY_BAG(1, "LUCKY_BAG", "福袋活动"),

    RED_PACKET(2, "RED_PACKET", "红包活动"),

    SCORE_EXCHANGE_LUCKY_BAG(3, "SCORE_EXCHANGE_LUCKY_BAG", "积分兑换福袋活动"),

    SCORE_EXCHANGE_GOLD(4, "SCORE_EXCHANGE_GOLD", "积分兑换金币活动")

    ;

    private Integer type;

    private String name;

    private String desc;

    private ActivityTypeEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    private static Map<Integer, ActivityTypeEnum> activityTypeEnumMap = new HashMap<>();

    private static Map<Integer, String> activityTypeMap = new HashMap<>();

    /**
     * 查询使用的map
     */
    private static Map<Integer, String> activityQueryUsedMap = new HashMap<>();

    static {
        for(ActivityTypeEnum item : ActivityTypeEnum.values()) {
            activityTypeEnumMap.put(item.getType(), item);
            activityTypeMap.put(item.getType(), item.getDesc());
            activityQueryUsedMap.put(item.getType(), item.getName());
        }
        activityTypeEnumMap.remove(ALL.getType());
        activityTypeMap.remove(ALL.getType());
    }

    public static Map<Integer, ActivityTypeEnum> getActivityTypeEnumMap() {
        return activityTypeEnumMap;
    }

    public static Map<Integer, String> getActivityQueryUsedMap() {
        return activityQueryUsedMap;
    }

    public static Map<Integer, String> getActivityTypeMap() {
        return activityTypeMap;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
