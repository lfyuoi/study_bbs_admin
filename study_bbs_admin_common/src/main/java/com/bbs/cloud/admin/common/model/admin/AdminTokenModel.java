package com.bbs.cloud.admin.common.model.admin;


import com.bbs.cloud.admin.common.enums.admin.AdminTokenStatusEnum;

import java.util.Date;

/**
 * 管理员令牌
 */
public class AdminTokenModel {

    private String id;

    private String userId;
    /**
     * 令牌有效期
     */
    private Date expired;

    /**
     * {@link AdminTokenStatusEnum}
     */
    private Integer status;

    private String ticket;

}
