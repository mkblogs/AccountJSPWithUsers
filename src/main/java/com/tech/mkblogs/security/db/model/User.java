package com.tech.mkblogs.security.db.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"authorities","settings"})
@NoArgsConstructor
public class User extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String loginName;
	private String password;
	private LocalDateTime lastLogin;
	private Boolean accountExpired; 
	private Boolean accountLocked;
	private Boolean credentialsExpired;
	private Boolean enabled;
	
	private String createdName;
	private String lastModifiedName;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Authorities> authorities = new HashSet<Authorities>();
	
	public Authorities addAuthorities(Authorities authority) {
		getAuthorities().add(authority);
		authority.setUser(this);
		return authority;
	}
	
	public Authorities removeAuthorities(Authorities authority) {
		getAuthorities().remove(authority);
		authority.setUser(null);
		return authority;
	}
	
	public String getRole() {
		Set<Authorities> list = getAuthorities();
		String roleString = "";
		for(Authorities authorities :list) {
			roleString += authorities.getAuthority() + ",";
		}
		roleString = roleString.substring(0, roleString.length()-1);
		return roleString;
	}
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Settings settings;
	
}
