package com.tech.mkblogs.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tech.mkblogs.mapper.UserMapper;
import com.tech.mkblogs.security.db.AccountUserRepository;
import com.tech.mkblogs.security.db.dto.UserSessionDTO;
import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.session.operations.SessionOperations;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	AccountUserRepository repository;
	
	@Autowired
	SessionOperations sessionOperations; 
	
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        boolean admin = false;
        
        log.info("inside onAuthenticationSuccess(...) ");
        
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().contains("ROLE_ADMIN")){
                admin = true;
            }
        }
        
        try {
        	User loginUser = repository.findByLoginName(authentication.getName());
            if(loginUser != null) {
            	loginUser.setLastLogin(LocalDateTime.now().plusHours(5).plusMinutes(30));
        		repository.save(loginUser);
        		
        		UserSessionDTO userDTO = new UserSessionDTO();
        		userDTO = UserMapper.INSTANCE.toSessionUserDTO(loginUser);
        		sessionOperations.storeSession(userDTO);
            }else {
            	UserSessionDTO userDTO = new UserSessionDTO();
        		userDTO.setConnectionType("PLAIN_JDBC");
        		sessionOperations.storeSession(userDTO);
            }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
        if(admin){
          response.sendRedirect("/adminlanding");
        }else{
          response.sendRedirect("/userlanding");
        }
	}
	
}
