package com.hxline.thumbsservice.messaging.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.services.interfaces.ThumbServicesInterface;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 *
 * @author Handoyo
 */
public class ThumbSubscriber {
    
    private String serviceInstance;
    private ThumbServicesInterface thumbServices;
    private ConnectionFactory connectionFactory;
    private final String EXCHANGE = "fanout.thumb";

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public String getServiceInstance() {
        return serviceInstance;
    }

    public void setThumbServices(ThumbServicesInterface thumbServices) {
        this.thumbServices = thumbServices;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    public void consuming(){
        try {
            String queueName = "Thumb-Queue-" + serviceInstance;
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind("", EXCHANGE, "");
            channel.basicConsume(queueName, consumer);

            while (true) {
                try {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                    if (delivery == null) {
//                        break;
                    } else {
                        //hanya mengambil pesan dengan messageId selain miliknya sendiri
                        if (!delivery.getProperties().getType().equalsIgnoreCase(serviceInstance)) {
                            if (delivery.getEnvelope().isRedeliver()) {
                                System.out.println("Message has been delivered before.");
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
                    e.printStackTrace();
                    break;
                }
            }

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Boolean save(QueueingConsumer.Delivery delivery) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String msg = new String(delivery.getBody(), "UTF-8");
            thumbServices.saveQueue(mapper.readValue(msg, Thumb.class));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}