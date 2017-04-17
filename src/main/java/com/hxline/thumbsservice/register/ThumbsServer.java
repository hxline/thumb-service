package com.hxline.thumbsservice.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
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
@Import(ThumbRest.class)
@ImportResource({"classpath*:thumb-context.xml"})
public class ThumbsServer{

    private static String serviceInstance;
    private static ThumbServicesInterface thumbServices;
    private static ConnectionFactory connectionFactory;

    public static void setServiceInstance(String serviceInstance) {
        ThumbsServer.serviceInstance = serviceInstance;
    }

    public String getServiceInstance() {
        return serviceInstance;
    }

    public static void setThumbServices(ThumbServicesInterface thumbServices) {
        ThumbsServer.thumbServices = thumbServices;
    }

    public static void setConnectionFactory(ConnectionFactory connectionFactory) {
        ThumbsServer.connectionFactory = connectionFactory;
    }

    public static void main(String[] args) {
        try {
            System.setProperty("spring.config.name", "thumb-server");
            SpringApplication.run(ThumbsServer.class, args);
            String queueName = "Queue-" + serviceInstance;
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind("", "fanout.thumb", "");
            channel.basicConsume(queueName, consumer);

            while (true) {
                try {
                    Delivery delivery = consumer.nextDelivery();
                    long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                    if (delivery == null) {
//                        break;
                    } else {
                        //hanya mengambil pesan dengan messageId selain miliknya sendiri
                        if (!delivery.getProperties().getType().equalsIgnoreCase(serviceInstance)) {
                            if (delivery.getEnvelope().isRedeliver()) {
                                System.out.println("Message has been deliver before.");
                            } else {
                                if (save(delivery)) {
                                    channel.basicAck(deliveryTag, false);
                                    System.out.println("Message Delivered.");
                                }
                            }
                            
                        } else {
                            channel.basicAck(deliveryTag, false);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }

            channel.close();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static Boolean save(Delivery delivery) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String msg = new String(delivery.getBody(), "UTF-8");
            thumbServices.saveQueue(mapper.readValue(msg, Thumb.class));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
