package com.bbs.cloud.admin.test.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("endpoint")
public class TestEndpoint {

    @RequestMapping("feigh")
    public String  testFeigh(){
        System.out.println("进入feigh");
        return "hello feigh";
    }
}
