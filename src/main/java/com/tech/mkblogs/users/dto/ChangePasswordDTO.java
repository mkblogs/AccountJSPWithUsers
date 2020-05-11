package com.tech.mkblogs.users.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ValidPassword(message = "{changepassword.password.mustmatch}",verifyWithDB = "yes")
public class ChangePasswordDTO {

	private Integer id;	
	@NotEmpty(message = "{changepassword.currentPassword.notempty}")	
	private String currentPassword;
	@NotEmpty(message = "{changepassword.password.notempty}")
	@Size(min = 6,max = 8,message = "{changepassword.password.size}")
	private String password;
	@NotEmpty(message = "{changepassword.repeatPassword.notempty}")
	@Size(min = 6,max = 8,message = "{changepassword.repeatPassword.size}")
	private String repeatPassword;
	
	private String message;
}
