package com.bbs.cloud.admin.activity.endpoint;


import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activity/endpoint")
public class ActivityEndpoint {

    @Autowired
    private ActivityService activityService;
    @GetMapping("/gift/used/amount/query")
    public HttpResult<Integer> queryGiftTotalByType(@RequestParam("giftType") Integer giftType){

        return activityService.queryGiftTotalByType(giftType);
    }
}


