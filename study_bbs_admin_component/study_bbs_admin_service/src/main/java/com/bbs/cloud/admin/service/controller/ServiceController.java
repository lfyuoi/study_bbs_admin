package com.bbs.cloud.admin.service.controller;

import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {


    @Autowired
    private ServiceService serviceService;


    @PostMapping("/send/message")
    public HttpResult sendMessage(@RequestBody OrderMessageParam param) {
        return serviceService.sendMessage(param);
    }

    @GetMapping("/query")
    public HttpResult queryService() {
        return null;
    }
}
