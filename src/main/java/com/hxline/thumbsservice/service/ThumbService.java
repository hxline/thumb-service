package com.hxline.thumbsservice.service;

import com.hxline.thumbsservice.domain.Thumb;
import com.hxline.thumbsservice.hibernate.ThumbHibernate;
import com.hxline.thumbsservice.service.interfaced.ThumbInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Handoyo
 */
@RestController
public class ThumbService implements ThumbInterface{
    
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    @HystrixCommand(
            fallbackMethod = "saveFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
        )
    public ResponseEntity<Void> save(@RequestBody Thumb thumb){
        thumbHibernate().save(thumb);
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
        return new ResponseEntity(thumbHibernate().getAll(), HttpStatus.FOUND);
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
        Thumb thumb = thumbHibernate().get(id);
        if (thumb != null) {
            return new ResponseEntity(thumb, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        
    }
    
    public ResponseEntity<Thumb> getFallback(@PathVariable("id") String id){
        return new ResponseEntity(null, HttpStatus.REQUEST_TIMEOUT);
    }
    
    private ThumbHibernate thumbHibernate(){
        Resource r = new ClassPathResource("application-context.xml");
        BeanFactory factory = new XmlBeanFactory(r);
        ThumbHibernate thumb = (ThumbHibernate) factory.getBean("thumbHibernate");
        return thumb;
    }
}