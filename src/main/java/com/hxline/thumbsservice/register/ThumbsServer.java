package com.hxline.thumbsservice.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.messaging.consumer.ThumbConsumer;
import com.hxline.thumbsservice.rest.ThumbRest;
import com.hxline.thumbsservice.services.interfaces.ThumbServicesInterface;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author Handoyo
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@Import({ThumbRest.class})
@ImportResource({"classpath*:thumb-context.xml"})
public class ThumbsServer{

    private static ThumbConsumer thumbConsumer;

    public static void setThumbConsumer(ThumbConsumer thumbConsumer) {
        ThumbsServer.thumbConsumer = thumbConsumer;
    }
    
    public static void main(String[] args) {
        try {
            System.setProperty("spring.config.name", "thumb-server");
            SpringApplication.run(ThumbsServer.class, args);
            thumbConsumer.consuming();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}