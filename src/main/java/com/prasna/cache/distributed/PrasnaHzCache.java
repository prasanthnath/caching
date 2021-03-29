package com.prasna.cache.distributed;

import com.prasna.cache.PrasnaReaderCache;
import com.prasna.cache.CacheConstants;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class PrasnaHzCache<T, R> implements PrasnaReaderCache<T, R> {
    private final Map<T, R> cache;

    public PrasnaHzCache() {
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