package com.hxline.thumbsservice.rest;

import com.hxline.thumbsservice.domain.Thumb;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hxline.thumbsservice.rest.interfaces.ThumbRestInterface;
import com.hxline.thumbsservice.services.interfaces.ThumbServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Handoyo
 */
@RestController
public class ThumbRest implements ThumbRestInterface{
    
    @Autowired private ThumbServicesInterface thumbServices;
    
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @HystrixCommand(
            fallbackMethod = "saveFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<Void> save(@RequestBody Thumb thumb){
        thumbServices.save(thumb);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    public ResponseEntity<Void> saveFallback(@RequestBody Thumb thumb){
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }
    
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @HystrixCommand(
            fallbackMethod = "getAllFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<List<Thumb>> getAll(){
        return new ResponseEntity(thumbServices.getAll(), HttpStatus.FOUND);
    }
    
    public ResponseEntity<List<Thumb>> getAllFallback(){
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @HystrixCommand(
            fallbackMethod = "getFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<Thumb> get(@PathVariable("id") String id){
        Thumb thumb = thumbServices.get(id);
        if (thumb != null) {
            return new ResponseEntity(thumb, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        
    }
    
    public ResponseEntity<Thumb> getFallback(@PathVariable("id") String id){
        return new ResponseEntity(null, HttpStatus.REQUEST_TIMEOUT);
    }
}