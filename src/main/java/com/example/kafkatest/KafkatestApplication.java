package com.example.kafkatest;

import com.example.kafkatest.producer.MyProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

@Slf4j
@SpringBootApplication
public class KafkatestApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkatestApplication.class, args);
    }

    // SpringBootApplication 실행 후 특정 작업 실행
    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate,
                                    KafkaMessageListenerContainer<String, String> container) {
        return args -> {
            // 실행 내용 정의 - 실행되면서 kafka의 mytest2 topic의 messages 전송
            // kafka producer는 KafkaTemplate을 이용해서 생성
            kafkaTemplate.send("springexam","professional of host bar, JBJ");
            container.start();

//            log.info("============== pause ==============");
//            container.pause();
//
//            Thread.sleep(4000);
//            log.info("============== resume ==============");
//            container.resume();
//
//            log.info("============== stop ==============");
//            container.stop();

        };
    }
}
