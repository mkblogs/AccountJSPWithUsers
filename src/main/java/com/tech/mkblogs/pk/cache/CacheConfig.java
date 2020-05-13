package com.tech.mkblogs.pk.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{

	public static final String CACHE_NAME = "auto-increment-cache";
	
	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration cache = new CacheConfiguration();
		cache.setName(CACHE_NAME);
		cache.setMaxEntriesLocalHeap(10);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(cache);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}
	
	@Bean
	public CacheManager cacheManager( ) {
		net.sf.ehcache.CacheManager ehCacheCacheManager = ehCacheManager();
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager( ehCacheCacheManager );
		cacheManager.setTransactionAware( true );
		return cacheManager;
	}

}
