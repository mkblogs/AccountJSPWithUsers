package com.tech.mkblogs.pk.series;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesTableRepository extends JpaRepository<SeriesTable, Integer> {

	@Query("SELECT coalesce(max(series.currentValue), 0) FROM SeriesTable series")
	Long getMaxSeriesValue();
	
	List<SeriesTable> findByTableName(String tableName);
}
