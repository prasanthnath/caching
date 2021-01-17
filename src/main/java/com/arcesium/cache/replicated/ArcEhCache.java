package com.arcesium.cache.replicated;

import com.arcesium.cache.ArcReaderCache;
import com.arcesium.cache.CacheConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ArcEhCache<T, R> implements ArcReaderCache<T, R> {
    private Cache cacheStore;

    public ArcEhCache(){
        CacheManager cacheManager = CacheManager.create(new EhCacheConfig().build());
        cacheStore = cacheManager.getCache(CacheConstants.DEFAULT_CACHE_NAME);
    }

    @Override
    public void put(T key, R value) {
        try {
            cacheStore.acquireWriteLockOnKey(key);
            cacheStore.put(new Element(key, value));
        } finally {
            cacheStore.releaseWriteLockOnKey(key);
        }
    }

    @Override
    public R putIfAbsent(T key, R value) {
        Element element = cacheStore.putIfAbsent(new Element(key, value));
        return element == null ? null : (R)element.getObjectValue();
    }

    @Override
    public R get(T key) {
        Element element = cacheStore.get(key);
        return element == null ? null : (R)element.getObjectValue();
    }

    @Override
    public boolean remove(T key) {
        return cacheStore.remove(key);
    }

    @Override
    public boolean remove(T key, R value) {
        Element existing = cacheStore.removeAndReturnElement(key);
        Object asset = existing != null ? existing.getObjectValue() : null;
        boolean valid = Objects.equals(value, asset);
        if(!valid){
            cacheStore.put(existing);
        }
        return valid;
    }

    @Override
    public void clear() {
        cacheStore.removeAll();
    }
}