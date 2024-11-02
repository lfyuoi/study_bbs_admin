package com.bbs.cloud.admin.service.result.vo;

import java.util.List;
import java.util.Map;

public class ServiceGiftVO {

    private Integer serviceType;

    private List<GiftVO> giftList;

    private Map<Integer, Map<String,String>> giftDescMap;

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public List<GiftVO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftVO> giftList) {
        this.giftList = giftList;
    }

    public Map<Integer, Map<String, String>> getGiftDescMap() {
        return giftDescMap;
    }

    public void setGiftDescMap(Map<Integer, Map<String, String>> giftDescMap) {
        this.giftDescMap = giftDescMap;
    }
}
