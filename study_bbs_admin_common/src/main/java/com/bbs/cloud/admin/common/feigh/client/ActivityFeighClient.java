package com.bbs.cloud.admin.common.feigh.client;

import com.bbs.cloud.admin.common.feigh.factory.ActivityFeighFactory;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "activity", fallbackFactory = ActivityFeighFactory.class)
public interface ActivityFeighClient {

    @GetMapping("activity/endpoint/gift/used/amount/query")
    public HttpResult<Integer> queryServiceGiftTotal(@RequestParam("giftType") Integer giftType);

    @GetMapping("activity/endpoint/gold/used/query")
    public HttpResult<Integer> queryUsedGold();

}
