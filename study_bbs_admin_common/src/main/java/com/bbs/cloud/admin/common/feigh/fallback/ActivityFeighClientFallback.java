package com.bbs.cloud.admin.common.feigh.fallback;

import com.bbs.cloud.admin.common.feigh.client.ActivityFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ActivityFeighClientFallback implements ActivityFeighClient {

    @Override
    public HttpResult<Integer> queryServiceGiftTotal(@RequestParam("giftType") Integer giftType) {
        return null;
    }

    @Override
    public HttpResult<Integer> queryUsedGold() {
        return null;
    }
}
