package com.example.kafkatest.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@Slf4j
public class ConsumerDefaultMessageListener implements MessageListener<String,String> {
    // 메시지 전송시 onMessage() 호출되고, 데이터가 ConsumerRecord 형태로 전달된다.
    // record로 전달되는 방식은 데이터가 각각 한 건씩 전달되는 방식(단일메시지).
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        log.info("listener: {}",data);
    }
}
