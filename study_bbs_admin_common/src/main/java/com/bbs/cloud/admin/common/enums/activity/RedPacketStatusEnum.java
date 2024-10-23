package com.bbs.cloud.admin.common.enums.activity;

import java.util.HashMap;
import java.util.Map;

public enum RedPacketStatusEnum {

    NORMAL(1, "NORMAL", "正常待领取"),

    GETED(2, "DEL", "已领取"),

    INVALID(3, "INVALID", "失效")

    ;

    private Integer status;

    private String name;

    private String desc;

    RedPacketStatusEnum(Integer status, String name, String desc) {
        this.status = status;
        this.name = name;
        this.desc = desc;
    }

    private static Map<Integer, String> redPacketStatusMap = new HashMap<>();

    static {
        for(RedPacketStatusEnum temp : RedPacketStatusEnum.values()) {
            redPacketStatusMap.put(temp.getStatus(), temp.getDesc());
        }
    }

    public static Map<Integer, String> getRedPacketStatusMap() {
        return redPacketStatusMap;
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
