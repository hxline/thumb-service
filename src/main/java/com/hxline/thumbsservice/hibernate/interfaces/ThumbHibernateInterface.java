package com.hxline.thumbsservice.hibernate.interfaces;

import com.hxline.thumbsservice.domain.Thumb;
import java.util.List;

/**
 *
 * @author Handoyo
 */
public interface ThumbHibernateInterface {
    public void save(Thumb thumb);
    
    public void saveQueue(Thumb thumb);

    public List<Thumb> getAll();

    public Thumb get(String id);
}