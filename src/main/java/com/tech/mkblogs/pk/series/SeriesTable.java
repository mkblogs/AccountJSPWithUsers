package com.tech.mkblogs.pk.series;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EntityListeners(AuditingEntityListener.class)
public class SeriesTable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String tableName;
	
	private Integer currentValue;
	
	@CreatedBy
	private Integer createdBy;
	
	private String createdName;
	
	@CreatedDate
	private LocalDateTime createdTs;
	
	@LastModifiedBy
	private Integer lastModifiedBy;
	
	private String lastModifiedName;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedTs;
	
	@Version
	private Integer version;
}
