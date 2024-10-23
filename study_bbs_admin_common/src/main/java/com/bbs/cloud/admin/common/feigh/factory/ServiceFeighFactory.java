package com.bbs.cloud.admin.common.feigh.factory;

import com.bbs.cloud.admin.common.feigh.client.ServiceFeighClient;
import com.bbs.cloud.admin.common.feigh.client.TestFeighClient;
import com.bbs.cloud.admin.common.feigh.fallback.ServiceFeighClientFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceFeighFactory implements FallbackFactory<ServiceFeighClient> {

    private final ServiceFeighClientFallback serviceFeighClientFallback;

    public ServiceFeighFactory(ServiceFeighClientFallback serviceFeighClientFallback) {
        this.serviceFeighClientFallback = serviceFeighClientFallback;
    }

    @Override
    public ServiceFeighClient create(Throwable throwable) {
        System.out.println("异常打印......");
        throwable.printStackTrace();
        return serviceFeighClientFallback;
    }
}
