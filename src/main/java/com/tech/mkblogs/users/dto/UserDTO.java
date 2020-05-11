package com.tech.mkblogs.users.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;
import com.tech.mkblogs.validation.UniqueValueValidator.UniqueValue;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@UniqueValue(message = "{user.username.alreadyexists}",
    methodName = "findAllByLoginName",className = "com.tech.mkblogs.users.service.UserService")
@ValidPassword(message = "{user.password.mustmatch}",verifyWithDB = "no")
public class UserDTO {

	private Integer id;
	@NotEmpty(message = "{user.username.notempty}")
	@Size(min = 2,max = 50,message = "{user.username.size}")
	private String username;
	@NotEmpty(message = "{user.password.notempty}")
	@Size(min = 6,max = 8,message = "{user.password.size}")
	private String password;
	@NotEmpty(message = "{user.repeatPassword.notempty}")
	@Size(min = 6,max = 8,message = "{user.repeatPassword.size}")
	private String repeatPassword;
	
	@NotEmpty(message = "{user.authType.notempty}")
	private String authType;
	
	private Boolean authEncrypted;
	
	@NotEmpty(message = "{user.connectionType.notempty}")
	private String connectionType;
	@NotEmpty(message = "{user.primaryKeyGenerationType.notempty}")
	private String primaryKeyGenerationType;
	
	private Boolean expired; 
	private Boolean locked;
	private Boolean credentialsExpired;
	private Boolean enabled;
	
	@NotEmpty(message = "{user.role.notempty}")
	private String role;
	
	private String lastLogin;
	
	private String message;
	
}
