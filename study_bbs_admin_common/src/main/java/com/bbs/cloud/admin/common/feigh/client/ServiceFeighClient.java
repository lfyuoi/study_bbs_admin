package com.bbs.cloud.admin.common.feigh.client;

import com.bbs.cloud.admin.common.feigh.factory.ServiceFeighFactory;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service", fallbackFactory = ServiceFeighFactory.class)
public interface ServiceFeighClient {

    @GetMapping("service/endpoint/gift/total/query")
    public HttpResult<Integer> queryServiceGiftTotal();

    @GetMapping("service/endpoint/gilt/list/query")
    public HttpResult<String> queryServiceGiftList();


    @PostMapping("service/endpoint/gilt/list/update")
    public HttpResult updateServiceGiftList(@RequestParam("data") String data);


    @GetMapping("service/endpoint/gold/query")
    public HttpResult<Integer> queryServiceGold();


    @PostMapping("service/endpoint/gold/update")
    public HttpResult updateServiceGold(@RequestParam("usedGold") Integer usedGold);
}
