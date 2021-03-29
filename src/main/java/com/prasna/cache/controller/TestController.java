package com.prasna.cache.controller;

import com.prasna.cache.PrasnaReaderCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gullapal
 */
@RestController
@RequestMapping("/cache")
public class TestController {
    @Autowired
    private List<PrasnaReaderCache<String, String>> readerCaches;

    @PostMapping(path = "/{keyVal}")
    public String cache(@PathVariable String keyVal) {
        String[] split = keyVal.split(":");
        if(split.length < 2) {
            throw new IllegalArgumentException("Key:Value syntax not followed!");
        }
        readerCaches.forEach(cache -> cache.put(split[0], split[1]));
        return returnValue(split[0]);
    }

    @GetMapping(value = "/{key}")
    public String getCachedValues(@PathVariable(name = "key") String key) {
        return returnValue(key);
    }

    @NotNull
    private String returnValue(String key) {
        return readerCaches.stream().map(cache -> {
            String cacheName = cache.getClass().getSimpleName();
            String cachedVal = Optional.ofNullable(cache.get(key)).orElse("");
            return cacheName + ": " + cachedVal;
        }).collect(Collectors.joining("; "));
    }
}