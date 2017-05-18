package com.hxline.thumbsservice.register;

import com.hxline.thumbsservice.config.CertificateConfiguration;
import com.hxline.thumbsservice.messaging.subscriber.ThumbKafkaSubscriber;
import com.hxline.thumbsservice.messaging.subscriber.ThumbSubscriber;
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
@Import({ThumbRest.class})
@ImportResource({"classpath*:thumb-context.xml"})
public class ThumbsServer {

    //Kafka
    private static CertificateConfiguration certificateConfiguration;
    private static ThumbKafkaSubscriber thumbKafkaSubscriber;

    public static void setCertificateConfiguration(CertificateConfiguration certificateConfiguration) {
        ThumbsServer.certificateConfiguration = certificateConfiguration;
    }

    public static void setThumbKafkaSubscriber(ThumbKafkaSubscriber thumbKafkaSubscriber) {
        ThumbsServer.thumbKafkaSubscriber = thumbKafkaSubscriber;
    }
    //end Kafka
    
    //RabbitMQ
    private static ThumbSubscriber thumbSubscriber;

    public static void setThumbSubscriber(ThumbSubscriber thumbSubscriber) {
        ThumbsServer.thumbSubscriber = thumbSubscriber;
    }
    //end RabbitMQ
    
    public static void main(String[] args) {
        try {
            System.setProperty("spring.config.name", "thumb-server");
            SpringApplication.run(ThumbsServer.class, args);
            //RabbitMQ
//            thumbSubscriber.consuming();
            //end RabbitMQ
            
            //Kafka
            certificateConfiguration.create();
            thumbKafkaSubscriber.consuming();
            //end Kafka
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
