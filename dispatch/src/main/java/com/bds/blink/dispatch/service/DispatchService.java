package com.bds.blink.dispatch.service;

import java.util.UUID;
import static java.util.UUID.randomUUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bds.blink.dispatch.message.OrderCreated;
import com.bds.blink.dispatch.message.OrderDispatched;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchService {

    private static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    private static final UUID APPLICATION_UUID = randomUUID();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void process(OrderCreated orderCreated) throws Exception {
        OrderDispatched orderDispatched = OrderDispatched
                .builder()
                .orderId(orderCreated.getOrderId())
                .processedById(APPLICATION_UUID)
                .note("Dispatched: " + orderCreated.getItem())
                .build();
        kafkaTemplate.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();

        log.info("Sent message: orderId: " + orderCreated.getOrderId() + " processed by: ", APPLICATION_UUID);
    }
}
