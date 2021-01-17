package com.arcesium.cache.distributed;

import com.arcesium.cache.ArcReaderCache;
import com.arcesium.cache.CacheConstants;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ArcHzCache<T, R> implements ArcReaderCache<T, R> {
    private Map<T, R> cache;

    public ArcHzCache() {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(new HzCacheConfig().build());
        this.cache = hazelcastInstance.getMap(CacheConstants.DEFAULT_CACHE_NAME);
    }

    @Override
    public void put(T key, R value) {
        cache.put(key, value);
    }

    @Override
    public R putIfAbsent(T key, R value) {
        return cache.putIfAbsent(key, value);
    }

    @Override
    public R get(T key) {
        return cache.get(key);
    }

    @Override
    public boolean remove(T key) {
        return cache.remove(key) != null;
    }

    @Override
    public boolean remove(T key, R value) {
        return cache.remove(key, value);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}