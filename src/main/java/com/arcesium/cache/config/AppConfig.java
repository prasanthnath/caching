package com.arcesium.cache.config;

import com.arcesium.cache.distributed.ArcHzCache;
import com.arcesium.cache.offheap.ArcChronicleMapCache;
import com.arcesium.cache.replicated.ArcEhCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gullapal
 */
@Configuration
@ComponentScan(basePackages = { "com.arcesium.cache" })
public class AppConfig {

    @Bean
    public ArcChronicleMapCache<String, String> arcChronicleMapCache(){
        return new ArcChronicleMapCache<>("sample", "sample", 100);
    }

    @Bean
    public ArcHzCache<String, String> arcHzCache(){
        return new ArcHzCache<>();
    }

    @Bean
    public ArcEhCache<String, String> arcEhCache(){
        return new ArcEhCache<>();
    }
}
