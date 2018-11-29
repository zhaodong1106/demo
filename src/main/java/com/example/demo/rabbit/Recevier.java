//package com.example.demo.rabbit;
//
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by T011689 on 2018/11/20.
// */
//@Component
//public class Recevier {
//    private static final DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    @RabbitListener(queues = "hello.queue1")
//    public void handleMessage( String msg,Message message,Channel channel) throws IOException {
//        System.out.println(Thread.currentThread().getName() + " 接收到来自hello.queue1队列的消息：" + msg);
//        System.out.println(LocalDateTime.now().format(dtf));
//        try {
//            Thread.sleep(100);
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (Exception e) {
//            e.printStackTrace();
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        }
//
//    }
//
//
//    @RabbitListener(queues = "orderDelay.key")
//    public void processMessage2(String msg) {
//        System.out.println(Thread.currentThread().getName() + " 接收到来自hello.queue2队列的消息：" + msg);
//    }
//
//    @RabbitListener(queues = "delayed.goods.order")
//    public void process(String msg) {
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("接收时间:" + LocalDateTime.now().format(dtf));
//        System.out.println("消息内容：" + msg);
//        if(params.containsKey("reciveCount")) {
//            params.put("reciveCount", params.get("reciveCount") + 1);
//        }else {
//            params.put("reciveCount",1);
//        }
//        System.out.println("reciveCount:"+params.get("reciveCount"));
//    }
//    private static final ConcurrentHashMap<String,Integer> params=new ConcurrentHashMap<>();
//}
