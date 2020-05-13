package com.tech.mkblogs.service.jpa.simple;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.model.Account;

@Repository
public interface JPAAccountRepository 
			extends JpaRepository<Account, Integer>,JpaSpecificationExecutor<Account> {

	List<Account> findByAccountName(String accountName);
	List<Account> findByAmount(BigDecimal amount);
	
	@Query("SELECT coalesce(max(account.id), 0) FROM Account account")
	Long getMaxPKValue();
	
	@Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value="alter table account CHANGE id id INT(11) NOT NULL", nativeQuery = true)
    void removeAutoIncrementValue();
	
	@Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value="ALTER TABLE account CHANGE id id INT(11) NOT NULL AUTO_INCREMENT", nativeQuery = true)
    void addAutoIncrement();
	
	@Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value="ALTER TABLE account AUTO_INCREMENT=:value", nativeQuery = true)
    void addAutoIncrementValue(@Param("value") Integer value);
}
