package com.tech.mkblogs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.tech.mkblogs.security.db.dto.UserSessionDTO;
import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.users.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mappings({
	      @Mapping(target="id", source="user.id"),
	      @Mapping(target="loginName", source="user.loginName"),
	      @Mapping(target="lastLogin", source="user.lastLogin"),
	      
	      @Mapping(target="connectionType", source="user.settings.connectionType"),
	      @Mapping(target="primaryKeyGenerationType", source="user.settings.primaryKeyGenerationType"),
	      @Mapping(target="authenticationType", source="user.settings.authenticationType"),
	      @Mapping(target="authenticationEncrypted", source="user.settings.authenticationEncrypted")
	    })
	UserSessionDTO toSessionUserDTO(User user);
	
	@Mappings({
	      @Mapping(target="id", source="user.id"),
	      @Mapping(target="username", source="user.loginName"),
	      @Mapping(target="lastLogin", source="user.lastLogin"),
	      
	      @Mapping(target="connectionType", source="user.settings.connectionType"),
	      @Mapping(target="primaryKeyGenerationType", source="user.settings.primaryKeyGenerationType"),
	      @Mapping(target="authType", source="user.settings.authenticationType"),
	      @Mapping(target="authEncrypted", source="user.settings.authenticationEncrypted"),
	      @Mapping(target="role", source="user.role")
	    })
	UserDTO toUserDTO(User user);
	
	@Mappings({
	      
	      @Mapping(source="userDTO.username", target="loginName"),
	      @Mapping(source="userDTO.password", target="password"),
	      @Mapping(source="connectionType", target="settings.connectionType"),
	      @Mapping(source="primaryKeyGenerationType", target="settings.primaryKeyGenerationType"),
	      @Mapping(source="userDTO.authType", target="settings.authenticationType"),
	      @Mapping(source="userDTO.authEncrypted", target="settings.authenticationEncrypted")
	      
	    })
	User toUser(UserDTO userDTO);
	
}