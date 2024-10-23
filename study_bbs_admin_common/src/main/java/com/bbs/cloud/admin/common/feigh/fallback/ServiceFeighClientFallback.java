package com.bbs.cloud.admin.common.feigh.fallback;

import com.bbs.cloud.admin.common.feigh.client.ServiceFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.stereotype.Component;

@Component
public class ServiceFeighClientFallback implements ServiceFeighClient {

    @Override
    public HttpResult<Integer> queryServiceGiftTotal() {
        return null;
    }

    @Override
    public HttpResult<String> queryServiceGiftList() {
        return null;
    }

    @Override
    public HttpResult updateServiceGiftList(String data) {
        return null;
    }

    @Override
    public HttpResult<Integer> queryServiceGold() {
        return null;
    }

    @Override
    public HttpResult updateServiceGold(Integer usedGold) {
        return null;
    }
}
