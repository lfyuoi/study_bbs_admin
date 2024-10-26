package com.bbs.cloud.admin.activity.mapper;

import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface ActivityMapper{

    ActivityDTO queryActivityByType(@Param("activityType") Integer activityType, @Param("statusList") List<Integer> asList);

    void insertActivityDTO(ActivityDTO activityDTO);

    ActivityDTO queryActivityById(@Param("id") String id);

    void updateActivity(ActivityDTO activityDTO);

    List<ActivityDTO> queryActivityList();

//    Integer queryActivityCountByCondition(ActivityConditionDTO conditionDTO);
//
//    List<ActivityDTO> queryActivityByCondition(ActivityConditionDTO conditionDTO);
}
