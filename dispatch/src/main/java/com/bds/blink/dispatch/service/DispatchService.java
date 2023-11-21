package com.bds.blink.dispatch.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bds.blink.dispatch.message.OrderCreated;
import com.bds.blink.dispatch.message.OrderDispatched;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void process(OrderCreated orderCreated) throws Exception {
        OrderDispatched orderDispatched = OrderDispatched.builder().orderId(orderCreated.getOrderId()).build();
        kafkaTemplate.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
    }
}
