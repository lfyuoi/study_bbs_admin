package com.bbs.cloud.admin.activity.mapper;

import com.bbs.cloud.admin.activity.dto.RedPacketDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RedPacketMapper {
    void insertRedPacketList(@Param("data") List<RedPacketDTO> data);

    Integer queryActivityRedPacket(@Param("status") Integer status);

    List<RedPacketDTO> queryRedPacketList(@Param("activityId") String activityId);

    void updateRedPacket(@Param("activityId") String activityId, @Param("status") Integer status, @Param("toStatus") Integer toStatus);

    void updateRedPacketById(@Param("id") String id, @Param("status") Integer status);

    Integer queryRedPacketAmountByStatus(@Param("activityId") String activityId, @Param("status") Integer status);
}
