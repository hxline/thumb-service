package com.hxline.thumbsservice.messaging.publisher;

import com.hxline.thumbsservice.domain.Thumb;
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

/**
 *
 * @author Handoyo
 */
public class ThumbKafkaPublisher extends Thread {
    
    private String serviceInstance;
    private Thumb thumb;

    public ThumbKafkaPublisher(String serviceInstance, Thumb thumb, String name) {
        super(name);
        this.serviceInstance = serviceInstance;
        this.thumb = thumb;
    }    
    
    public Boolean send(Thumb thumb){
        try {
            String brokers = "steamer-01.srvs.cloudkafka.com:9093";
            String topicPrefix = "wmju-thumb";

            Properties props = new Properties();
            props.put("bootstrap.servers", brokers);
//            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class.getName());
            props.put("value.serializer", com.hxline.thumbsservice.domain.serialization.ThumbSerializer.class.getName());
            props.put("security.protocol", "SSL");
            props.put("ssl.truststore.location", "truststore.jks");
            props.put("ssl.truststore.password", "123qweasd");
            props.put("ssl.keystore.location", "keystore.jks");
            props.put("ssl.keystore.password", "123qweasd");
            props.put("ssl.keypassword", "123qweasd");
            
            KafkaProducer producer = new KafkaProducer(props);
            producer.send(new ProducerRecord<>(topicPrefix, serviceInstance, thumb));
            System.out.println("Sent : " + thumb.toString());
            producer.close();
            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.getName().equalsIgnoreCase(thumb.toString())) {
                    thread.interrupt();
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {            
            send(thumb);
        }
    }
}