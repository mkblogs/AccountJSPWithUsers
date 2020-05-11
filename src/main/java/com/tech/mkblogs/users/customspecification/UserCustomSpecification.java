package com.tech.mkblogs.users.customspecification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.security.db.model.Authorities;
import com.tech.mkblogs.security.db.model.Settings;
import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.users.dto.UserDTO;

public class UserCustomSpecification implements Specification<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2065807339220549456L;
	
	UserDTO userDTO;
	
	public UserCustomSpecification(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		String username = userDTO.getUsername();
		String authType = userDTO.getAuthType();
		String connectionType = userDTO.getConnectionType();
		String role = userDTO.getRole();
		
		Join<User,Settings> userJoin = root.join("settings");
		Join<User,Authorities> prodRelation = root.join("authorities");
		
		if(!StringUtils.isEmpty(authType)) {
			predicates.add(criteriaBuilder.equal(userJoin.get("authenticationType"),  authType));
		}
		
		if(!StringUtils.isEmpty(connectionType)) {
			predicates.add(criteriaBuilder.equal(userJoin.get("connectionType"),  connectionType));
		}
		
		if(!StringUtils.isEmpty(username)) {
			predicates.add(criteriaBuilder.like(root.get("loginName"),  "%"+username+"%"));
		}
		
		if(!StringUtils.isEmpty(role)) {
			predicates.add(criteriaBuilder.equal(prodRelation.get("authority"),  role));
		}
		
		 return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
	}
}
