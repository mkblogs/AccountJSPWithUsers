package com.tech.mkblogs.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalTime;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.users.dto.ChangePasswordDTO;
import com.tech.mkblogs.users.dto.UserDTO;
import com.tech.mkblogs.users.service.UserService;
import com.tech.mkblogs.validation.PasswordsEqualConstraintValidator.ValidPassword;

import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class PasswordsEqualConstraintValidator implements ConstraintValidator<ValidPassword, Object> {
	
	
	@Autowired
	private UserService userService;
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.ANNOTATION_TYPE,ElementType.TYPE })
	@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
	public @interface ValidPassword {
		 String message() default "The passwords must match";
		 Class<?>[] groups() default {};
		 Class<? extends Payload>[] payload() default {};
		 String verifyWithDB();
	}
	
	String verifyWithDB;
	
	@Override
    public void initialize(ValidPassword validPassword) {
		if(!StringUtils.isEmpty(validPassword.verifyWithDB())) {
			this.verifyWithDB = validPassword.verifyWithDB();
		}else {
			this.verifyWithDB = "NO";
		}
    }

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValid() " + LocalTime.now());
		boolean valid = true;
        try{
        	System.out.println(value);
        	if(value instanceof UserDTO) {
        		UserDTO userDTO = (UserDTO) value;
        		return isValidForRegistration(userDTO);
        	}else {
        		ChangePasswordDTO passwordDTO = (ChangePasswordDTO) value;
        		return isValidCheckForChangePassword(passwordDTO,context);
        	}        	
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return valid;
	}
	
	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	public Boolean isValidForRegistration(UserDTO userDTO) {
		String firstObj = userDTO.getPassword();
    	String secondObj = userDTO.getRepeatPassword();
    	
        Boolean isValid =  firstObj == null && secondObj == null || firstObj != null && 
        		firstObj.equalsIgnoreCase(secondObj);
        return isValid;
		
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean isValidCheckForChangePassword (Object value,ConstraintValidatorContext context) {
		ChangePasswordDTO passwordDTO = (ChangePasswordDTO) value;
		Boolean isMatchedWithDB = userService.passwordSameWithDB(passwordDTO);
		if(isMatchedWithDB) {
			String firstObj = passwordDTO.getPassword();
        	String secondObj = passwordDTO.getRepeatPassword();
        	
            Boolean isValid =  firstObj == null && secondObj == null || firstObj != null && 
            		firstObj.equalsIgnoreCase(secondObj);
            return isValid;
		}else {
			String newMessage = "{changepassword.password.notsameasdb}";
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(newMessage).addConstraintViolation();
			return false;
		}
	}
}
