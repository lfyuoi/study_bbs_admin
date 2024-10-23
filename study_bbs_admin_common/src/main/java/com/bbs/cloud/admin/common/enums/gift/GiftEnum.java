package com.bbs.cloud.admin.common.enums.gift;

import java.util.HashMap;
import java.util.Map;

/**
 * 礼物
 */
public enum GiftEnum {

    ROSE(1, 1, "ROSE", "玫瑰"),

    BEER(2, 2, "BEER", "啤酒"),

    CHEER(3, 4, "CHEER", "加油"),

    FLOWER(4, 8, "FLOWER", "鲜花"),

    LOLLY(5, 10, "LOLLY", "棒棒糖"),

    KISS(6, 20, "KISS", "飞吻"),

    GHARRY(7, 200, "GHARRY", "马车"),

    CAR(8, 1800, "CAR", "跑车"),

    PLANE(9, 5000, "PLANE", "私人飞机"),

    ROCKET(10, 10000, "ROCKET", "大火箭")

    ;

    private Integer giftType;

    private Integer price;

    private String name;

    private String desc;

    private GiftEnum(Integer giftType, Integer price, String name, String desc) {
        this.giftType = giftType;
        this.price = price;
        this.name = name;
        this.desc = desc;
    }

    private static final Map<Integer, GiftEnum> giftsMap = new HashMap<>();

    private static final Map<Integer, Map<String, String>> giftsJsonMap = new HashMap<>();

    static {
        for(GiftEnum giftEnum : GiftEnum.values()) {
            giftsMap.put(giftEnum.getGiftType(), giftEnum);

            Map<String, String> map = new HashMap<>();
            map.put("giftType", String.valueOf(giftEnum.getGiftType()));
            map.put("price", String.valueOf(giftEnum.getPrice()));
            map.put("name", giftEnum.getName());
            map.put("desc", giftEnum.getDesc());
            giftsJsonMap.put(giftEnum.getGiftType(), map);
        }
    }

    public static Map<Integer, GiftEnum> getGiftsMap() {
        return giftsMap;
    }

    public static Map<Integer, Map<String, String>> getGiftsJsonMap() {
        return giftsJsonMap;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
