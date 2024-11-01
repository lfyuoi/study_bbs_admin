package com.bbs.cloud.admin.service.mapper;

import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceGiftMapper {

    ServiceGiftDTO queryGiftDTO(@Param("giftType") Integer giftType);

    void insertGiftDTO(ServiceGiftDTO giftDTO);

    void updateGiftDTO(ServiceGiftDTO giftDTO);

    List<ServiceGiftDTO> queryGiftDTOList();

    Integer queryGiftAmount();


    void updateGiftDTOList(@Param("list") List<ServiceGiftDTO> data);
}
