package com.bbs.cloud.admin.service.endpoint;

import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("service/endpoint")
public class ServiceEndpoint {

    @Autowired
    private ServiceService serviceService;

    @GetMapping ("/gift/total/query")
    public HttpResult<Integer> queryServiceGiftTotal() {
        return serviceService.queryServiceGiftTotal();

    }

    @GetMapping("/gift/list/query")
    public  HttpResult<String> queryServiceGiftList(){
        return serviceService.queryServiceGiftList();
    }

    @PostMapping("/gift/list/update")
    public  HttpResult updateServiceGiftList(@RequestParam("data") String data){
        return serviceService.updateServiceGiftList(data);
    }
}
