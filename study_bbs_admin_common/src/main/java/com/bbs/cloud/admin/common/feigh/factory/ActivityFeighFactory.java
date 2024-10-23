package com.bbs.cloud.admin.common.feigh.factory;

import com.bbs.cloud.admin.common.feigh.client.ActivityFeighClient;
import com.bbs.cloud.admin.common.feigh.client.ServiceFeighClient;
import com.bbs.cloud.admin.common.feigh.fallback.ActivityFeighClientFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityFeighFactory implements FallbackFactory<ActivityFeighClient> {

    private final ActivityFeighClientFallback activityFeighClientFallback;

    public ActivityFeighFactory(ActivityFeighClientFallback activityFeighClientFallback) {
        this.activityFeighClientFallback = activityFeighClientFallback;
    }

    @Override
    public ActivityFeighClient create(Throwable throwable) {
        System.out.println("异常打印......");
        throwable.printStackTrace();
        return activityFeighClientFallback;
    }
}
