package com.example.kafkatest.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public void send(){
        kafkaTemplate.send("springexam","THIS IS THE MOST INSANE COMMUNICATION");
    }

    // KafkaTemplate을 이용해서 async에 대한 callback 처리
    public void async(String topic, String message){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new KafkaSendCallback<>(){
            // 메시지 보내기 실패시 callback으로 호출
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                log.info("메시지 전송 성공: {}",result);
                log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            }

            // 메시지 보내기 실패시 callback으로 호출
            @Override
            public void onFailure(KafkaProducerException ex) {
                // 메시지 전송 실패 시 넘겨받는 object를 이용해 정보 출력
                ProducerRecord<Object, Object> failedProducerRecord = ex.getFailedProducerRecord();
                log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                log.info("메시지 전송 실패: {}",failedProducerRecord);
                log.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            }

        });
    }

    // kafka sync communication
    public void sync(String topic, String message){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        try {
            // get()을 하면 sync
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
