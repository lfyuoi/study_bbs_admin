package com.bbs.cloud.admin.activity.mapper;

import com.bbs.cloud.admin.activity.dto.LuckyBagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LuckyBagMapper {
    void insertLuckyBag(@Param("data") List<LuckyBagDTO> list);

    List<LuckyBagDTO> queryLuckyBag(@Param("activityId") String activityId);

    void updateLuckyBag(@Param("activityId") String activityId, @Param("toStatus") Integer toStatus, @Param("status") Integer status);

    Integer queryGiftAmount(@Param("giftType") Integer giftType, @Param("data") List<Integer> statusList);

    void updateLuckyBagById(@Param("id") String id, @Param("status") Integer status);

    Integer queryLuckyBagAmountByStatus(@Param("activityId") String activityId, @Param("status") Integer status);
}
