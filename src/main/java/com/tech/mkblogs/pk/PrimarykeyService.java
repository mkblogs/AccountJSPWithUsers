package com.tech.mkblogs.pk;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.mkblogs.pk.atomic.AtomicService;
import com.tech.mkblogs.pk.auto.AutoSeqService;
import com.tech.mkblogs.pk.cache.CacheConfig;
import com.tech.mkblogs.pk.cache.CacheService;
import com.tech.mkblogs.pk.series.SeriesTable;
import com.tech.mkblogs.pk.series.SeriesTableRepository;
import com.tech.mkblogs.service.useraudit.UserAuditService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PrimarykeyService {

	@Autowired
	AutoSeqService autoSequenceService;
	
	@Autowired
	AtomicService atomicService;
	
	@Autowired
	CacheService cacheService;
	
	@Autowired
	SeriesTableRepository seriesRepository;
	
	@Autowired
	private UserAuditService auditService;
	
	String tableName = "Account";
	
	
	public void processPK(String primaryKeyGenerationType) {
		log.info("| Request Time - Start - processPK("+primaryKeyGenerationType+") " + LocalTime.now());
		Optional<Integer> optionalValue = null;
		Integer cacheValue = 0;
		try {
			optionalValue = cacheService.fetchCache(CacheConfig.CACHE_NAME, 0);
		}catch(Exception e) {
			log.error(e.getMessage());
			//e.printStackTrace();
		}
		if(optionalValue != null && optionalValue.isPresent()) {
			cacheValue = optionalValue.get();
		}
		System.out.println("Cache Value from Series ::"+cacheValue);
		if("AUTO".equalsIgnoreCase(primaryKeyGenerationType)) {
			autoSequenceService.addAutoIncrement(cacheValue);
		}else if("ATOMIC".equalsIgnoreCase(primaryKeyGenerationType)) {
			atomicService.setValue(cacheValue);
		}
		log.info("| Request Time - End - processPK() " + LocalTime.now());
	}
	
	public void updatePK(Integer newValue) throws Exception {
		log.info("| Request Time - Start - updatePK('"+newValue+"') " + LocalTime.now());
		List<SeriesTable> seriesList =  seriesRepository.findByTableName(tableName);
		if(seriesList.size() == 0) {
			SeriesTable seriesTable = new SeriesTable();
			seriesTable.setTableName(tableName);
			seriesTable.setCurrentValue(newValue);
			seriesTable.setCreatedName(auditService.getUserName());
			seriesRepository.save(seriesTable);
		}else if(seriesList.size() == 1) {
			SeriesTable seriesTable = seriesList.get(0);
			seriesTable.setCurrentValue(newValue);
			seriesTable.setLastModifiedName(auditService.getUserName());
			seriesRepository.save(seriesTable);
		}else {
			throw new RuntimeException("There is more series records for given table name value :"+tableName);
		}
		log.info("| Request Time - End - updatePK('"+newValue+"') " + LocalTime.now());
	}
	
	public Integer getNextSequence(String primaryKeyGenerationType) {
		log.info("| Request Time - Start - getNextSequence('"+primaryKeyGenerationType+"') " + LocalTime.now());
		if("ATOMIC".equalsIgnoreCase(primaryKeyGenerationType)) {
			atomicService.increment();
			return atomicService.getValue();
			
		}else if("SERIES".equalsIgnoreCase(primaryKeyGenerationType)) {
			List<SeriesTable> seriesList =  seriesRepository.findByTableName(tableName);
			if(seriesList.size() == 1) {
				SeriesTable seriesTable = seriesList.get(0);
				Integer currentValue = seriesTable.getCurrentValue();
				currentValue = currentValue +1;
				return currentValue;
			}else {
				throw new RuntimeException("There is more series records for given table name value :"+tableName);
			}
			
		}else if("CACHE".equalsIgnoreCase(primaryKeyGenerationType)) {
			Optional<Integer> optionalValue = null;
			Integer currentValue = 0;
			try {
				optionalValue = cacheService.fetchCache(CacheConfig.CACHE_NAME, 0);
			}catch(Exception e) {
				log.error(e.getMessage());
				throw new RuntimeException("There is an exception while fetching the next value from cache :"+e.getMessage());
			}
			if(optionalValue != null && optionalValue.isPresent()) {
				currentValue = optionalValue.get();
				currentValue = currentValue +1;
				return currentValue;
			}
		}
		return null;
	}
	
}
