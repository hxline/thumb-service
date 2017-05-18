package com.hxline.thumbsservice.messaging.subscriber;

import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.services.ThumbServices;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author Handoyo
 */
public class ThumbKafkaSubscriber {

    private String serviceInstance;
    private ThumbServices thumbServices;

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public void setThumbServices(ThumbServices thumbServices) {
        this.thumbServices = thumbServices;
    }

    public void consuming() {
        try {
            String brokers = "steamer-01.srvs.cloudkafka.com:9093";
            String topicPrefix = "wmju-thumb";

            Properties props = new Properties();
            props.put("bootstrap.servers", brokers);
            props.put("group.id", serviceInstance);
            props.put("auto.offset.reset", "earliest");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class.getName());
            props.put("value.deserializer", com.hxline.thumbsservice.domain.serialization.ThumbDeserializer.class.getName());
            props.put("security.protocol", "SSL");
            props.put("ssl.truststore.location", "/tmp/truststore.jks");
            props.put("ssl.truststore.password", "123qweasd");
            props.put("ssl.keystore.location", "/tmp/keystore.jks");
            props.put("ssl.keystore.password", "123qweasd");
            props.put("ssl.keypassword", "123qweasd");

            KafkaConsumer consumer = new KafkaConsumer(props);
            consumer.subscribe(Arrays.asList(topicPrefix));
            while (true) {
                ConsumerRecords<String, Thumb> records = consumer.poll(0);
                for (ConsumerRecord<String, Thumb> record : records) {
                    if (!record.key().equalsIgnoreCase(serviceInstance)) {
                        thumbServices.saveQueue(record.value());
                        System.out.println("Saved Key");
                        System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value().toString());
                    } else {
                        System.out.println("Denied Key");
                        System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value().toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}