package com.bbs.cloud.admin.common.feigh.factory;

import com.bbs.cloud.admin.common.feigh.client.TestFeighClient;
import com.bbs.cloud.admin.common.feigh.fallback.TestFeighClientFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class TestFeighFactory implements FallbackFactory<TestFeighClient> {

    private final TestFeighClientFallback testFeighClientFallback;

    public TestFeighFactory(TestFeighClientFallback testFeighClientFallback) {
        this.testFeighClientFallback = testFeighClientFallback;
    }

    @Override
    public TestFeighClient create(Throwable throwable) {
        System.out.println("异常打印......");
        throwable.printStackTrace();
        return testFeighClientFallback;
    }
}
