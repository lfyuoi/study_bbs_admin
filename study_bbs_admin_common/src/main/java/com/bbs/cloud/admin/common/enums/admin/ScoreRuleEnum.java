package com.bbs.cloud.admin.common.enums.admin;

/**
 * 积分规则枚举
 */
public enum ScoreRuleEnum {

    ESSAY(1, 1000, "ESSAY", "发帖操作"),

    COMMENT(2, 500, "COMMENT", "评论操作"),

    LIKE(3, 100, "LIKE", "被点赞")

    ;

    private Integer type;

    private Integer score;

    private String name;

    private String desc;

    private ScoreRuleEnum(Integer type, Integer score, String name, String desc) {
        this.type = type;
        this.score = score;
        this.name = name;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
