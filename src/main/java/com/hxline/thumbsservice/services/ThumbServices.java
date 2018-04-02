package com.hxline.thumbsservice.services;

import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.hibernate.interfaces.ThumbHibernateInterface;
import com.hxline.thumbsservice.services.interfaces.ThumbServicesInterface;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Handoyo
 */
@Service
public class ThumbServices implements ThumbServicesInterface{

    private ThumbHibernateInterface thumbHibernate;

    public void setThumbHibernate(ThumbHibernateInterface thumbHibernate) {
        this.thumbHibernate = thumbHibernate;
    }
    
    @Override
    public void save(Thumb thumb) {
        thumbHibernate.save(thumb);
    }

    @Override
    public void saveQueue(Thumb thumb) {
        thumbHibernate.saveQueue(thumb);
    }

    @Override
    public List<Thumb> getAll() {
        return thumbHibernate.getAll();
    }

    @Override
    public Thumb get(String id) {
        return thumbHibernate.get(id);
    }
}