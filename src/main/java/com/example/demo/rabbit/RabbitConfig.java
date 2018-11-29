//package com.example.demo.rabbit;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by T011689 on 2018/11/20.
// */
//@Configuration
//public class RabbitConfig {
//    //声明队列
//    @Bean
//    public Queue queue1() {
//        return new Queue("hello.queue1", true); // true表示持久化该队列
//    }
//
////    @Bean
////    public Queue queue2() {
////        return new Queue("hello.queue2", true);
////    }
//
//    //声明交互器
//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange("topicExchange");
//    }
//
//    //绑定
//    @Bean
//    public Binding binding1() {
//        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key.1");
//    }
//
////    @Bean
////    public Binding binding2() {
////        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.#");
////    }
////    @Bean
////    Queue delayQueuePerMessageTTL() {
////    return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
////            .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
////            .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
////            .build();
////    }
//
//    //声明交互器
//    @Bean
//    DirectExchange orderDirectExchange() {
//        return new DirectExchange("orderDelayExchange");
//    }
//
//    //绑定
//    @Bean
//    public Binding bindingOrderDirectExchange() {
//        return BindingBuilder.bind(delayQueuePerQueueTTL()).to(orderDirectExchange()).with("orderDelayExchange.key");
//    }
//    @Bean
//    Queue delayQueuePerQueueTTL() {
//        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
//                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME) // DLX
//                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
//                .withArgument("x-message-ttl", QUEUE_EXPIRATION) // 设置队列的过期时间
//                .build();
//    }
//    @Bean
//    Queue delayProcessQueue() {
//        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
//                .build();
//    }
//    @Bean
//    DirectExchange delayExchange() {
//        return new DirectExchange(DELAY_EXCHANGE_NAME);
//    }
//    @Bean
//    Binding dlxBinding() {
//        return BindingBuilder.bind(delayProcessQueue())
//                .to(delayExchange())
//                .with(DELAY_PROCESS_QUEUE_NAME);
//    }
//    private static final String DELAY_QUEUE_PER_QUEUE_TTL_NAME="orderDelayQueue";
//    private static final String DELAY_EXCHANGE_NAME="orderExchange";
//    private static  final String DELAY_PROCESS_QUEUE_NAME="orderDelay.key";
//    private static  final int QUEUE_EXPIRATION=30000;
//
//    public final static String QUEUE_NAME = "delayed.goods.order";
//    public  final static String EXCHANGE_NAME = "delayedec";
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME);
//    }
//
//    // 配置默认的交换机
//    @Bean
//    CustomExchange customExchange() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-delayed-type", "direct");
//        //参数二为类型：必须是x-delayed-message
//        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
//    }
//    // 绑定队列到交换器
//    @Bean
//    Binding binding(Queue queue, CustomExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME).noargs();
//    }
//
//
//}
