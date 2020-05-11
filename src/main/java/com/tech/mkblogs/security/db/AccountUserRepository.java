package com.tech.mkblogs.security.db;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.security.db.model.User;

@Repository
public interface AccountUserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

	User findByLoginName(String loginName);
    
	@Query("SELECT u FROM User u WHERE u.loginName = :loginName")
	public Collection<User> findAllByLoginName(@Param("loginName") String loginName);
	
}
