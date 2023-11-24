package com.bds.blink.dispatch.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.bds.blink.dispatch.exception.RetryableException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StockServiceClient {
    private final RestTemplate restTemplate;
    private final String stockServiceEndpoint;

    public StockServiceClient(@Autowired RestTemplate restTemplate,
            @Value("${dispatch.stockServiceEndpoint}") String stockServiceEndpoint) {
        this.restTemplate = restTemplate;
        this.stockServiceEndpoint = stockServiceEndpoint;
    }
    public String checkAvailability(String item) {
        try {
            return "true";
            // ResponseEntity<String> response = restTemplate.getForEntity(stockServiceEndpoint+"?item="+item, String.class);
            // if (response.getStatusCodeValue() != 200) {
            //     throw new RuntimeException("error " + response.getStatusCodeValue());
            // }
            // return response.getBody();
        } catch (HttpServerErrorException | ResourceAccessException e) {
            log.warn("Failure calling external service", e);
            throw new RetryableException(e);
        } catch (Exception e) {
            log.error("Exception thrown: " + e.getClass().getName(), e);
            throw e;
        }
    }
}
