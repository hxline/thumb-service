package com.hxline.thumbsservice.service.interfaced;

import com.hxline.thumbsservice.domain.Thumb;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Handoyo
 */
@FeignClient("thumb-service")
public interface ThumbInterface {
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity<Void> save(@RequestBody Thumb thumb);
    
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<List<Thumb>> getAll();
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Thumb> get(@PathVariable("id") String id);
}
