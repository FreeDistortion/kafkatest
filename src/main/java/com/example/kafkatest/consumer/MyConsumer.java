package com.example.kafkatest.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class MyConsumer {
    // kafka가 method를 호출하면서 parameter로 전송된 data를 전달
//    @KafkaListener(id = "groupid",topics = "mytest2",containerFactory = "messageListenerContainer")
    @KafkaListener(id = "groupid",topics = "mytest2",containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listen(String message){
        log.info("================================================");
        log.info(message);
        log.info("================================================");

    }
}
