package com.example.kafkatest.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyProducerTest {
    @Autowired
    private MyProducer myProducer;
    @Test
    void test(){
//        myProducer.send();
        myProducer.async("springexam","callbacktest");
//        myProducer.sync("springexam","synctest");
    }
}