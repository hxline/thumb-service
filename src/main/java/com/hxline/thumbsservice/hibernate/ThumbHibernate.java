package com.hxline.thumbsservice.hibernate;

import com.hxline.thumbsservice.component.HibernateRepository;
import com.hxline.thumbsservice.messaging.publisher.ThumbPublisher;
import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.hibernate.interfaces.ThumbHibernateInterface;
import com.hxline.thumbsservice.messaging.publisher.ThumbKafkaPublisher;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Handoyo
 */
@Repository
@Transactional
public class ThumbHibernate extends HibernateRepository implements ThumbHibernateInterface {

    //Kafka
    private String serviceInstance;
    private ThumbKafkaPublisher thumbKafkaPublisher;

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public void setThumbKafkaPublisher(ThumbKafkaPublisher thumbKafkaPublisher) {
        this.thumbKafkaPublisher = thumbKafkaPublisher;
    }
    //end Kafka

    //RabbitMQ
    private ThumbPublisher thumbPublisher;

    public void setThumbPublisher(ThumbPublisher thumbPublisher) {
        this.thumbPublisher = thumbPublisher;
    }
    //end RabbitMQ

    @Override
    public void save(Thumb thumb) {
        try {
            getSession().saveOrUpdate(thumb);
            //Kafka
            thumbKafkaPublisher = new ThumbKafkaPublisher(serviceInstance, thumb, thumb.toString());
            thumbKafkaPublisher.start();
            //end kafka
            
            //rabbitmq
            //thumbPublisher.send(thumb);
            //rabbitmq
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveQueue(Thumb thumb) {
        getSession().saveOrUpdate(thumb);
    }

    @Override
    public List<Thumb> getAll() {
        return new ArrayList(getSession().createQuery("FROM Thumb").list());
    }

    @Override
    public Thumb get(String id) {
        return getSession().get(Thumb.class, id);
    }
}
