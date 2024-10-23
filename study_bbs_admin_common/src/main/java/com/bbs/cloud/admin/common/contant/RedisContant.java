package com.bbs.cloud.admin.common.contant;

public class RedisContant {

    /**
     * 用来存放福袋活动中的福袋
     */
    public static final String BBS_CLOUD_ACTIVITY_LUCKY_BAG_LIST = "bbs:cloud:activity:lucky:bag:list";

    /**
     * 用来存放红包活动中的红包
     */
    public static final String BBS_CLOUD_ACTIVITY_RED_PACKET_LIST = "bbs:cloud:activity:red:packet:list";

    /**
     * 用来存放积分兑换福袋活动中的福袋
     */
    public static final String BBS_CLOUD_ACTIVITY_SCORE_LUCKY_BAG_LIST = "bbs:cloud:activity:score:lucky:bag:list";

    /**
     * 活动-积分兑换金币缓存key
     */
    public static final String BBS_CLOUD_ACTIVITY_SCORE_GOLD = "bbs:cloud:activity:score:lucky:gold";

    /**
     * 用户会话key
     */
    public static final String BBS_CLOUD_USER_TICKET_KEY = "bbs:cloud:user:ticket:key:";

    /**
     * 将服务组件的礼物都锁起来
     */
    public static final String BBS_CLOUD_LOCK_GIFT_KEY = "bbs:cloud:lock:gift:key";

    /**
     * 活动锁，防止重复操作
     */
    public static final String BBS_CLOUD_LOCK_ACTIVITY = "bbs:cloud:lock:activity:";


    /**
     * 将服务组件的金币都锁起来
     */
    public static final String BBS_CLOUD_LOCK_GOLD_KEY = "bbs:cloud:lock:gold:key";


}
