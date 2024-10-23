package com.bbs.cloud.admin.common.contant;

public class RabbitContant {

    /**
     * TEST组件需要定义的配置--------------start
     */
    public static final String TEST_EXCHANGE_NAME = "test_topic_exchange";

    public static final String TEST_QUEUE_NAME = "test_queue";

    public static final String TEST_ROUTING_KEY = "test_rounting";

    /**
     * TEST组件需要定义的配置--------------end
     */

    /**
     * SERVICE组件需要定义的配置--------------start
     */
    public static final String SERVICE_EXCHANGE_NAME = "service_topic_exchange";

    public static final String SERVICE_QUEUE_NAME = "service_queue";

    public static final String SERVICE_ROUTING_KEY = "service_rounting";

    /**
     * SERVICE组件需要定义的配置--------------end
     */

    /**
     * ACTIVITY组件需要定义的配置--------------start
     */
    public static final String ACTIVITY_EXCHANGE_NAME = "activity_topic_exchange";

    public static final String ACTIVITY_QUEUE_NAME = "activity_queue";

    public static final String ACTIVITY_ROUTING_KEY = "activity_rounting";
    /**
     * ACTIVITY组件需要定义的配置--------------end
     */

}
