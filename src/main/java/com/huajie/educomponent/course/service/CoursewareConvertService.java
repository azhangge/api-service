package com.huajie.educomponent.course.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CoursewareConvertService {

    //@RabbitListener(queues = "my-queue")
    public void receiveMessage(String message){
        System.out.println("接收到的字符串消息是 => " + message);
    }

}
