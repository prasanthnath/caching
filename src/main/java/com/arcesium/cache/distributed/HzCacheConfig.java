package com.arcesium.cache.distributed;

import com.arcesium.cache.CacheConstants;
import com.hazelcast.config.*;

/**
 * @author gullapal
 */
class HzCacheConfig {
    Config build() {
        Config config = new Config().setClusterName("Sample Hz Cluster");
        NetworkConfig network = config.getNetworkConfig();
        network.setPortAutoIncrement(true);

        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(true)
                .addMember("192.168.0.107");

        EvictionConfig evictionConfig = new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)
                .setSize(CacheConstants.CACHE_SIZE).setMaxSizePolicy(MaxSizePolicy.PER_NODE);
        MapConfig mapConfig = new MapConfig(CacheConstants.DEFAULT_CACHE_NAME).setEvictionConfig(evictionConfig)
                .setTimeToLiveSeconds(CacheConstants.TTL).setMaxIdleSeconds(CacheConstants.MAX_IDLE_SECS);
        config.getMapConfigs().put(CacheConstants.DEFAULT_CACHE_NAME, mapConfig);
        return config;
    }
}
