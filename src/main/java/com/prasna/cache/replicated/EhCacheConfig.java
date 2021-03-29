package com.prasna.cache.replicated;

import com.prasna.cache.CacheConstants;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * @author gullapal
 */
class EhCacheConfig {
    Configuration build(){
        String ehCacheJGroupsPort= System.getenv("arc.eh_jgroups_port");
        FactoryConfiguration factoryConfiguration = new FactoryConfiguration()
                .className("net.sf.ehcache.distribution.jgroups.JGroupsCacheManagerPeerProviderFactory")
                .properties("connect=TCP(bind_addr=localhost;bind_port="+ehCacheJGroupsPort+"):TCPPING(initial_hosts=localhost[7801],localhost[7802],localhost[7803];port_range=1;timeout=5000;num_initial_members=3):MERGE2(min_interval=3000;max_interval=5000):FD_ALL(interval=5000;timeout=20000):FD(timeout=5000;max_tries=48;):VERIFY_SUSPECT(timeout=1500):pbcast.NAKACK(retransmit_timeout=100,200,300,600,1200,2400,4800;discard_delivered_msgs=true):pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):pbcast.GMS(print_local_addr=true;join_timeout=5000)")
                .propertySeparator("::");

        CacheConfiguration.CacheEventListenerFactoryConfiguration eventListenerFactoryConfig =
                new CacheConfiguration.CacheEventListenerFactoryConfiguration();
        eventListenerFactoryConfig.className("net.sf.ehcache.distribution.jgroups.JGroupsCacheReplicatorFactory")
                .properties("replicateAsynchronously=true, replicatePuts=true, replicateUpdates=true, " +
                        "replicateUpdatesViaCopy=true, replicateRemovals=true");
        CacheConfiguration defaultCache =
                new CacheConfiguration(CacheConstants.DEFAULT_CACHE_NAME, CacheConstants.CACHE_SIZE)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
                .timeToLiveSeconds(CacheConstants.TTL).timeToIdleSeconds(CacheConstants.MAX_IDLE_SECS)
                .cacheEventListenerFactory(eventListenerFactoryConfig);
        return new Configuration().name("Sample EhCache Cluster")
                .cacheManagerPeerProviderFactory(factoryConfiguration)
                .cache(defaultCache);
    }
}
