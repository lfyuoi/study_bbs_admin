package com.bbs.cloud.admin.activity.controller;

import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.param.OperatorActivityParam;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/create")
    public HttpResult createActivity(@RequestBody CreateActivityParam param) {
        return activityService.createActivity(param);
    }

    @RequestMapping("/start")
    public HttpResult startActivity(@RequestBody OperatorActivityParam param){
        return activityService.startActivity(param);
    }

    @RequestMapping("/end")
    public HttpResult endActivity(@RequestBody OperatorActivityParam param){
        return activityService.endActivity(param);
    }

    @RequestMapping("/page/query")
    public HttpResult pageQueryActivity(){
        return null;
    }
}
