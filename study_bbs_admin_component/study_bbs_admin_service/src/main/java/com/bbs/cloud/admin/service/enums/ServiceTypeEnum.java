package com.bbs.cloud.admin.service.enums;

import java.util.HashMap;
import java.util.Map;

public enum ServiceTypeEnum {

    GIFT_MESSAGE(100, "GIFT_MESSAGE", "礼物服务"),

    RECHARGE_MESSAGE(101, "RECHARGE_MESSAGE", "充值服务")

    ;

    private Integer type;

    private String name;

    private String desc;

    private ServiceTypeEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    private static final Map<Integer, String> serviceTypeMap = new HashMap<>();

    static {
        for(ServiceTypeEnum item : ServiceTypeEnum.values()) {
            serviceTypeMap.put(item.getType(), item.getDesc());
        }
    }

    public static Map<Integer, String> getServiceTypeMap() {
        return serviceTypeMap;
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
