package com.arcesium.cache.controller;

import com.arcesium.cache.replicated.ArcEhCache;
import com.arcesium.cache.distributed.ArcHzCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gullapal
 */
@RestController
public class TestController {
    @Autowired
    private ArcEhCache arcEhCache;
    @Autowired
    private ArcHzCache arcHzCache;

    @RequestMapping(path = "/welcome")
    public String cache() {
        arcEhCache.put("1", "abc");
        arcHzCache.put("1", "abc");
        return "Welcome "+arcEhCache.get("1")+", "+arcHzCache.get("1");
    }

    @RequestMapping(path = "/")
    public String get() {
        return "Values "+arcEhCache.get("1")+", "+arcHzCache.get("1");
    }
}