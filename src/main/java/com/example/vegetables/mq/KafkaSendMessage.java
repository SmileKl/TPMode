package com.example.vegetables.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSendMessage {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void send() {
        // 发送消息
//        kafkaTemplate.send(topic, msg);
    }
}
