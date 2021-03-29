package com.prasna.cache.replicated;

import com.prasna.cache.PrasnaReaderCache;
import com.prasna.cache.CacheConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Objects;

public class PrasnaEhCache<T, R> implements PrasnaReaderCache<T, R> {
    private final CacheManager cacheManager;
    private final Cache cacheStore;

    public PrasnaEhCache(){
        cacheManager = CacheManager.create(new EhCacheConfig().build());
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