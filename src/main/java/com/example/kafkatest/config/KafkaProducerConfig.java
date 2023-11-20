package com.example.kafkatest.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    // KafkaTemplate object를 bean으로 등록
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return  new KafkaTemplate<>(producerFactory());
    }

    // Producer onject를 만들 수 있도록 ProducerFactory object 생성
    private ProducerFactory<String,String> producerFactory(){
        // implementation of ProducerFactory
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    // Kafka Producer에 대한 config
    private Map<String,Object> producerProps(){
        Map<String,Object> props = new HashMap<>();
        // kafka가 기본으로 제공하는 설정 적용
        // server
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.6:9092");
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        // key, value 둘 다 String으로 하겠다는 의미. 근데 네트워크는 패킷단위로 통신하므로 규칙에 맞는(직렬화) 타입 지정
        // key: key를 직렬화할 수 있는 Serializer object type으로 지정(문자열을 패킷으로 쪼개서 네트워크 환경에서 전송할 수 있는 타입으로 만들 수 있도록 Kafka에서 제공하는 class)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);

        return props;
    }

}