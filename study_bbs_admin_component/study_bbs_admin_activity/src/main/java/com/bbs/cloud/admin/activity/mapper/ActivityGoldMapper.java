package com.bbs.cloud.admin.activity.mapper;

import com.bbs.cloud.admin.activity.dto.ActivityGoldDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ActivityGoldMapper {

    /**
     * 记录积分兑换金币活动中，金币的使用情况
     * @param activityGoldDTO
     */
    void insertActivityGoldDTO(ActivityGoldDTO activityGoldDTO);

    /**
     * 根据活动ID查询活动金币使用情况
     * @param activityId
     * @return
     */
    ActivityGoldDTO queryActivityGoldDTOByActivityId(@Param("activityId") String activityId);

    /**
     * 更新金币使用情况
     * @param activityGoldDTO
     */
    void updateActivityGoldDTO(ActivityGoldDTO activityGoldDTO);

    Integer queryUsedAmountTotalByStatus(@Param("status") Integer status);

    ActivityGoldDTO queryActivityGoldDTOByStatus(Integer status);
}
