//package com.example.demo.rabbit;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.UUID;
//
///**
// * Created by T011689 on 2018/11/20.
// */
//@Component
//public class Sender implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
//    @Autowired(required = false)
//    private RabbitTemplate rabbitTemplate;
//    @PostConstruct
//    public void init(){
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnCallback(this);
//    }
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (ack) {
//            System.out.println("消息发送成功:" + correlationData);
//        } else {
//            System.out.println("消息发送失败:" + cause);
//        }
//    }
//
//    @Override
//    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//        System.out.println(new String(message.getBody(), Charset.forName("utf-8")) + " 发送失败");
//    }
//
//    //发送消息，不需要实现任何接口，供外部调用。
//    public void send(String msg){
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//        System.out.println("开始发送消息 : " + msg.toLowerCase());
//         rabbitTemplate.convertSendAndReceive("orderExchange", "key.1", msg, correlationId);
////        System.out.println("结束发送消息 : " + msg.toLowerCase());
////        System.out.println("消费者响应 : " + response + " 消息处理完成");
//    }
//}
