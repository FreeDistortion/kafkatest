package com.example.kafkatest.config;

import com.example.kafkatest.consumer.listener.ConsumerDefaultMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    // annotation을 사용하기 위한 container
    // ConcurrentKafkaListenerContainerFactory는 annotation으로 활용할 수 있고, listener를 직접 활용 가능
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> concurrentKafkaListenerContainerFactory= new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public KafkaMessageListenerContainer<String, String> messageListenerContainer() {
        ContainerProperties props = new ContainerProperties("springexam");
        // props 세팅
        // KafkaMessageListenerContainer를 이용해서 작업하는 경우 groupid 정의는 필수
        // Consumer를 만들 때엔 기본으로 groupid 설정, 얘가 있어야 client id가 생성되고 서버에서 인지할 수 있다.
        props.setGroupId("springexamgroup");
        props.setAckMode(ContainerProperties.AckMode.BATCH);
        props.setMessageListener(new ConsumerDefaultMessageListener());

        // container object 생성
        KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(consumerFactory(),props);

        // 원하는 시점에 작동시키기 위한 설정
        container.setAutoStartup(false);

        return container;
        // return new KafkaMessageListenerContainer<>(consumerFactory())
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(producerProps());
    }

    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        // topic에 저장된 데이터를 가져올 때 필요한 설정(network로 전송되어 들어오는 데이터를 역직렬화)
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.13:9092");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.6:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }

}
