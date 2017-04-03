package com.hxline.thumbsservice.services.interfaces;

import com.hxline.thumbsservice.domain.Thumb;
import java.util.List;

/**
 *
 * @author Handoyo
 */
public interface ThumbServicesInterface {

    public void save(Thumb thumb);

    public List<Thumb> getAll();

    public Thumb get(String id);
}
