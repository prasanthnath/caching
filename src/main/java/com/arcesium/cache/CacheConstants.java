package com.arcesium.cache;

/**
 * @author gullapal
 */
public final class CacheConstants {
    public static final String DEFAULT_CACHE_NAME = "sample";
    public static final int TTL = 600;
    public static final int MAX_IDLE_SECS = 300;
    public static final int CACHE_SIZE = 1000;
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
}
