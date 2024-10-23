package com.bbs.cloud.admin.common.model.service;

/**
 * 系统金钱管理
 */
public class ServiceGoldModel {

    private String id;

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
