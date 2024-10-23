package com.bbs.cloud.admin.service.dto;


import com.bbs.cloud.admin.common.enums.gift.GiftEnum;

/**
 * 礼物
 */
public class ServiceGiftDTO {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Integer usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Integer getUnusedAmount() {
        return unusedAmount;
    }

    public void setUnusedAmount(Integer unusedAmount) {
        this.unusedAmount = unusedAmount;
    }
}
