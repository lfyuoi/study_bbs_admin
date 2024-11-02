package com.bbs.cloud.admin.activity.controller;

import com.bbs.cloud.admin.activity.param.CreateActivityParam;
import com.bbs.cloud.admin.activity.param.OperatorActivityParam;
import com.bbs.cloud.admin.activity.param.QueryActivityPageByConditionParam;
import com.bbs.cloud.admin.activity.result.vo.ActivityVO;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.ExcelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/page/condition/query")
    public HttpResult queryActivityPageByCondition(@RequestBody QueryActivityPageByConditionParam param){
        return activityService.queryActivityPageByCondition(param);
    }

    @RequestMapping("/export")
    public void exportActivityList(HttpServletResponse response){
        String fileName = CommonUtil.createUUID();
        String sheetName = "活动列表";
        String[] headers = {"ID","名称","内容","创建时间"};
        String[] propertys = {"id","name","content","createDate"};
        List data = activityService.queryActivityList();
        List<ActivityVO> activityVOS = new ArrayList<>();
        data.forEach(item -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(item,activityVO);
            activityVOS.add(activityVO);
        });
        ExcelUtil.exportExcel(response,fileName,sheetName,headers,propertys,activityVOS);


    }
}
