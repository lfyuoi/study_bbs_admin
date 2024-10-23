package com.bbs.cloud.admin.common.feigh.client;

import com.bbs.cloud.admin.common.feigh.factory.TestFeighFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test", fallbackFactory = TestFeighFactory.class)
public interface TestFeighClient {

    @GetMapping("/endpoint/feigh")
    public String testFeigh();

}
