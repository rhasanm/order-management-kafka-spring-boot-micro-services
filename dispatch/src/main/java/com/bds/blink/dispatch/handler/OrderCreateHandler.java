package com.bds.blink.dispatch.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
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
    public void listen(@Header(KafkaHeaders.RECEIVED_PARTITION) int partition, @Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload OrderCreated payload) {
        log.info("Received payload: partition: " + partition + ", key: " + key + ", payload: " + payload);
        try {
            dispatchService.process(key, payload);
        } catch (Exception e) {
            log.error("Processing failure", e.getCause());
        }
    }
}
