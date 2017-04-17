package com.hxline.thumbsservice.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Handoyo
 */
public class ThumbAMQP {

//    @Value("${eureka.instance.instanceId}")
    private String serviceInstance = "thumb1";
    private ConnectionFactory connectionFactory;
    private BasicProperties properties = new BasicProperties().builder()
            .expiration("1000000") //message otomatis di hapus setelah 16.6 menit
            .type(serviceInstance)
            .build();
    
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Boolean send(Thumb thumb) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
             
            channel.basicPublish("fanout.thumb", "", properties, mapper.writeValueAsString(thumb).getBytes());
            System.out.println("Sent : Success");

            channel.close();
            connection.close();
            
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}