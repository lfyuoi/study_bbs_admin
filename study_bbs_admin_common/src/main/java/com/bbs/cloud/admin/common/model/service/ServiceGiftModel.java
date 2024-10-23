package com.bbs.cloud.admin.common.model.service;


import com.bbs.cloud.admin.common.enums.gift.GiftEnum;

/**
 * 礼物
 */
public class ServiceGiftModel {

    private String id;

    /**
     * 礼物 {@link GiftEnum}
     */
    private Integer giftType;

    /**
     * 礼物总数量
     */
    private Integer amount;

    /**
     * 已使用礼物数量
     */
    private Integer usedAmount;

    /**
     * 未使用礼物数量
     */
    private Integer unusedAmount;

}
