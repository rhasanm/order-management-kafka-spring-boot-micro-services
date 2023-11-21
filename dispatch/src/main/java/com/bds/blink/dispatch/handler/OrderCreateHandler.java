package com.bds.blink.dispatch.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bds.blink.dispatch.message.OrderCreated;
import com.bds.blink.dispatch.service.DispatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreateHandler {
    private final DispatchService dispatchService;

    @KafkaListener(id = "OrderConsumerClient", topics = "order.created", groupId = "dispatch.order.created.consumer", containerFactory = "kafkaListenerContainerFactory")
    public void listen(OrderCreated payload) {
        log.info("Received payload: " + payload);
        dispatchService.process(payload);
    }
}
