package com.prasna.cache.config;

import com.prasna.cache.distributed.PrasnaHzCache;
import com.prasna.cache.offheap.PrasnaChronicleMapCache;
import com.prasna.cache.replicated.PrasnaEhCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gullapal
 */
@Configuration
@ComponentScan(basePackages = { "com.prasna.cache" })
public class AppConfig {

    @Bean
    public PrasnaChronicleMapCache<String, String> arcChronicleMapCache(){
        return new PrasnaChronicleMapCache<>("sample", "sample", 100);
    }

    @Bean
    public PrasnaHzCache<String, String> arcHzCache(){
        return new PrasnaHzCache<>();
    }

    @Bean
    public PrasnaEhCache<String, String> arcEhCache(){
        return new PrasnaEhCache<>();
    }
}
