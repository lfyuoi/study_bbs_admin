package com.bbs.cloud.admin.service.result;

import com.bbs.cloud.admin.service.result.vo.ServiceGiftVO;
import com.bbs.cloud.admin.service.result.vo.ServiceGoldVO;

import java.util.Map;

public class ServiceInfoResult {

    private ServiceGiftVO serviceGiftVO;

    private ServiceGoldVO serviceGoldVO;

    private Map<Integer, String> serviceType;

    public ServiceGiftVO getServiceGiftVO() {
        return serviceGiftVO;
    }

    public void setServiceGiftVO(ServiceGiftVO serviceGiftVO) {
        this.serviceGiftVO = serviceGiftVO;
    }

    public ServiceGoldVO getServiceGoldVO() {
        return serviceGoldVO;
    }

    public void setServiceGoldVO(ServiceGoldVO serviceGoldVO) {
        this.serviceGoldVO = serviceGoldVO;
    }

    public Map<Integer, String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Map<Integer, String> serviceType) {
        this.serviceType = serviceType;
    }
}
