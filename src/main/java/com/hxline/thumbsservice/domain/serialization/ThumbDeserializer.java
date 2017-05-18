package com.hxline.thumbsservice.domain.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 *
 * @author hxline
 */
public class ThumbDeserializer implements Deserializer<Thumb> {

    @Override
    public void configure(Map<String, ?> map, boolean bln) {

    }

    @Override
    public Thumb deserialize(String string, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Thumb thumb = null;

        try {
            thumb = mapper.readValue(bytes, Thumb.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return thumb;
    }

    @Override
    public void close() {

    }
}