package com.hxline.thumbsservice.domain.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.thumbsservice.domain.Thumb;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 *
 * @author hxline
 */
public class ThumbSerializer implements Serializer<Thumb> {

    @Override
    public void configure(Map<String, ?> map, boolean bln) {
        
    }

    @Override
    public byte[] serialize(String string, Thumb thumb) {
        byte[] thumbSerialize = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            thumbSerialize = objectMapper.writeValueAsString(thumb).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return thumbSerialize;
    }

    @Override
    public void close() {
        
    }
}
