package com.arcesium.cache.offheap;

import com.arcesium.cache.ArcReaderCache;
import com.arcesium.cache.CacheConstants;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArcChronicleMapCache<T, R> implements ArcReaderCache<T, R> {
    private final ChronicleMap<T, R> cache;
    private final Path persistedFilePath;

    public ArcChronicleMapCache(T sampleT, R sampleR, long size) {
        ChronicleMapBuilder<T, R> builder = ChronicleMapBuilder
                .of((Class<T>)sampleT.getClass(), (Class<R>)sampleR.getClass())
                .name("Sample Chronicle Map")
                .averageKey(sampleT)
                .averageValue(sampleR)
                .entries(size);
        try {
            this.persistedFilePath = Paths.get(CacheConstants.TEMP_DIR + "sample.dat");
            this.cache = builder.createPersistedTo(persistedFilePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @PreDestroy
    public void cleanUp(){
        cache.close();
        try {
            Files.deleteIfExists(persistedFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
