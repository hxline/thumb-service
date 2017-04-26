package com.hxline.thumbsservice.hibernate;

import com.hxline.thumbsservice.component.HibernateRepository;
import com.hxline.thumbsservice.messaging.publisher.ThumbPublisher;
import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.hibernate.interfaces.ThumbHibernateInterface;
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
public class ThumbHibernate extends HibernateRepository implements ThumbHibernateInterface{

    private ThumbPublisher thumbPublisher;

    public void setThumbPublisher(ThumbPublisher thumbPublisher) {
        this.thumbPublisher = thumbPublisher;
    }
    
    @Override
    public void save(Thumb thumb) {
        getSession().saveOrUpdate(thumb);
        thumbPublisher.send(thumb);
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