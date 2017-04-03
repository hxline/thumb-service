package com.hxline.thumbsservice.register;

import com.hxline.thumbsservice.rest.ThumbRest;
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
@Import(ThumbRest.class)
@ImportResource({"classpath*:thumb-context.xml"})
public class ThumbsServer {
    
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "thumb-server");

        SpringApplication.run(ThumbsServer.class, args);
    }
}