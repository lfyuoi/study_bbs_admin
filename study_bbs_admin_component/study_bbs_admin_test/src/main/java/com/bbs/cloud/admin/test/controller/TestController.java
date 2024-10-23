package com.bbs.cloud.admin.test.controller;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.feigh.client.TestFeighClient;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JedisUtil;
import com.bbs.cloud.admin.common.util.RedisOperator;
import com.bbs.cloud.admin.test.service.TestService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;



@RestController
@RequestMapping("test")
public class TestController {


    final static  org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("com.bbs.cloud.admin.common.feigh.client.TestFeighClient")
    private TestFeighClient  testFeighClient;


    @RequestMapping("/hello")
    public HttpResult hello(){
        logger.info("进入Hello接口");
        return new HttpResult("hello word,欢迎");
    }

    @RequestMapping("/db")
    public HttpResult db(){
        logger.info("进入db测试接口");
        return testService.queryTest();
    }
    @RequestMapping("/redis")
    public HttpResult redis(){
        logger.info("进入redis测试接口");
        jedisUtil.set("test-jedis","test-jedis-value");
        logger.info("jedis输出 {}",jedisUtil.get("test-jedis"));
        redisOperator.set("test-redis-operator","test-redis-operator-value");
        logger.info("redisOperator输出 {}",redisOperator.get("test-redis-operator"));
        return HttpResult.ok();
    }

    @RequestMapping("/mq")
    public HttpResult mq() {
        logger.info("进入mq测试接口");
        rabbitTemplate.convertAndSend(RabbitContant.TEST_EXCHANGE_NAME, RabbitContant.TEST_ROUTING_KEY, "Hello word,欢迎每个人");
        return testService.queryTest();
    }

    @RequestMapping("/feigh")
    public HttpResult feigh(){
        logger.info("进入feigh接口");
        String str = testFeighClient.testFeigh();
        return new HttpResult(str);
    }
}
