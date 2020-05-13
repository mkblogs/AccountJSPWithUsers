package com.tech.mkblogs.pk.cache;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	@Cacheable(value=CacheConfig.CACHE_NAME, key = "#cacheId",sync = true) 
	public Optional<Integer> fetchCache(String cacheId,Integer updatedValue) throws InterruptedException {
		return Optional.of(updatedValue);
	}
	
	@CacheEvict(value=CacheConfig.CACHE_NAME, allEntries=true)
	public void flushCache() { }
}
