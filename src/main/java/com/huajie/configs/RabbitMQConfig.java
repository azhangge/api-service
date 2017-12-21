package com.huajie.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;


//@PropertySource("classpath:rabbitmq.properties")
public class RabbitMQConfig {
    /**
     * 声明接收字符串的队列
     *
     * @return
     */
//    @Bean
    public Queue stringQueue() {
        return new Queue("my-queue");
    }

}
