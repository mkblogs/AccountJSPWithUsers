package com.tech.mkblogs.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.pk.cache.CacheConfig;
import com.tech.mkblogs.pk.cache.CacheService;
import com.tech.mkblogs.pk.series.SeriesTableRepository;
import com.tech.mkblogs.service.jpa.simple.JPAAccountRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OnStartup {

	@Autowired
	SeriesTableRepository seriesRepository;
	
	@Autowired
	JPAAccountRepository accountRepository;
	
	@Autowired
	CacheService cacheService;
	
	@EventListener(ApplicationReadyEvent.class)
	public void vlidatePrimaryKey() throws Exception {
		Long seriesCount = seriesRepository.getMaxSeriesValue();
		Long accountCount = accountRepository.getMaxPKValue();
		
		if(seriesCount != accountCount) {
			System.err.println("There is mismatch between series data and account data ");
			System.exit(0);
		}else {
			cacheService.fetchCache(CacheConfig.CACHE_NAME, seriesCount.intValue());
			log.info("Yes Startup okay ::"+seriesCount);
		}
	}
}
