package com.bbs.cloud.admin.service.dto;

/**
 * 系统金钱管理
 */
public class ServiceGoldDTO {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getUsedGold() {
        return usedGold;
    }

    public void setUsedGold(Integer usedGold) {
        this.usedGold = usedGold;
    }

    public Integer getUnusedGold() {
        return unusedGold;
    }

    public void setUnusedGold(Integer unusedGold) {
        this.unusedGold = unusedGold;
    }

    /**
     * 指定一个唯一名称
     */
    private String name;

    /**
     * 金钱总数量
     */
    private Integer gold;

    /**
     * 已使用金钱
     */
    private Integer usedGold;

    /**
     * 未使用金钱
     */
    private Integer unusedGold;

}
