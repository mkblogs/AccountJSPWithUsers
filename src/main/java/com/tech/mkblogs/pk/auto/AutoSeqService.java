package com.tech.mkblogs.pk.auto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.service.jpa.simple.JPAAccountRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AutoSeqService {

	@Autowired
	DataSource hikariDataSource;
	
	@Autowired
	JPAAccountRepository accountRepository;
	
	public void removeAutoIncrement() {
		log.info("| Request Time - Start - removeAutoIncrement() " + LocalTime.now());
		accountRepository.removeAutoIncrementValue();
		log.info("| Request Time - End - removeAutoIncrement() " + LocalTime.now());
	}
	
	public void addAutoIncrement(Integer value) {
		log.info("| Request Time - Start - addAutoIncrement() " + LocalTime.now());
		String sql = "select id from account";
		boolean isAutoPK = false;
		try {
			Connection connection = hikariDataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData metadata = resultSet.getMetaData();
			if (metadata.isAutoIncrement(1)) {
				isAutoPK = true;
			}
			if(!isAutoPK) {
				accountRepository.addAutoIncrement();
				accountRepository.addAutoIncrementValue(value);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("| Request Time - End - addAutoIncrement() " + LocalTime.now());
	}
}
