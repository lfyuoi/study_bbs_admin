package com.bbs.cloud.admin.service.param;

import java.util.Date;

public class OrderMessageParam {

    private  String id ;

    /**
     * {@link com.bbs.cloud.admin.service.enums.ServiceTypeEnum}
     */
    private Integer serviceType;

    private String serviceName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
}
