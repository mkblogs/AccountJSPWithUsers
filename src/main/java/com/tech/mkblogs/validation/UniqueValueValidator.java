package com.tech.mkblogs.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tech.mkblogs.dto.AccountDTO;
import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.users.dto.UserDTO;
import com.tech.mkblogs.validation.UniqueValueValidator.UniqueValue;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@SuppressWarnings("unchecked")
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object>{

	@Autowired
    private ApplicationContext applicationContext;
	
    private String className;
    private String methodName;
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
	@Constraint(validatedBy = UniqueValueValidator.class)
	public @interface UniqueValue {
		String message() default "Invalid value";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	    
	    String methodName();
	    String className();
	}
	

	@Override
    public void initialize(UniqueValue unique) {
		this.methodName = unique.methodName();
		this.className = unique.className();
	}
	 
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValid() " + LocalTime.now());
		if(value instanceof AccountDTO) {
			AccountDTO inputValue = (AccountDTO) value;
			return isValidAccount(inputValue, context);
		}else if(value instanceof UserDTO) {
			UserDTO inputValue = (UserDTO) value;
			return isValidUserObject(inputValue, context);
		}
		return true;
	}
	
	
	public boolean isValidAccount(AccountDTO inputValue, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValidAccount() " + LocalTime.now());
		boolean flag = true;
		if(inputValue.getAccountId() == null) {
			flag = isValidObjectForSave(inputValue, context);
		}else {
			flag = isValidObjectForUpdate(inputValue, context);
		}
		return flag;
	}
	
	protected boolean isValidObjectForSave(AccountDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			List<AccountDTO> dbAccountList = (List<AccountDTO>) method.invoke(serviceObject, inputValue.getName()); 
            if(dbAccountList.size() > 0)
    			statusFlag = false; //Failure
            
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	protected boolean isValidObjectForUpdate(AccountDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		int counter = 0;
		
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			List<AccountDTO> dbAccountList = (List<AccountDTO>) method.invoke(serviceObject, inputValue.getName());
			if (dbAccountList.stream().anyMatch(account -> account.getAccountId() != inputValue.getAccountId())) {
				counter++;
			}
			if (counter > 0)
				statusFlag = false;
            
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	public boolean isValidUserObject(UserDTO userDTO, ConstraintValidatorContext context) {
		log.info("| Request Time - Start - isValidUserObject() " + LocalTime.now());
		boolean flag = true;
		if(userDTO.getId() == null) {
			flag = isValidObjectForSaveForUsername(userDTO, context);
		}else {
			flag = isValidObjectForUpdateForUser(userDTO, context);
		}
		return flag;
	}
	
	protected boolean isValidObjectForSaveForUsername(UserDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			Collection<User> dbUser = (Collection<User>) method.invoke(serviceObject, inputValue.getUsername());
			
			 if(dbUser.size() > 0)
    			statusFlag = false; //Failure
            
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}
	
	protected boolean isValidObjectForUpdateForUser(UserDTO inputValue,ConstraintValidatorContext context) {
		boolean statusFlag = true; //Default Success
		int counter = 0;
		
		try {
			Class<?> clazz = Class.forName(this.className);
			Object serviceObject = applicationContext.getBean(clazz);
			Method method = serviceObject.getClass().getMethod(this.methodName, String.class);
			List<User> dbUserList = (List<User>) method.invoke(serviceObject, inputValue.getUsername());
			if (dbUserList.stream().anyMatch(user -> user.getId() != inputValue.getId())) {
				counter++;
			}
			if (counter > 0)
				statusFlag = false;
            
		}catch(Exception e) {
			e.printStackTrace();
		}
		return statusFlag;
	}

	
}
