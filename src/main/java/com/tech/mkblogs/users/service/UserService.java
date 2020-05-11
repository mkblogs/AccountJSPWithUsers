package com.tech.mkblogs.users.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.mkblogs.mapper.UserMapper;
import com.tech.mkblogs.security.db.AccountUserRepository;
import com.tech.mkblogs.security.db.model.Authorities;
import com.tech.mkblogs.security.db.model.Settings;
import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.service.useraudit.UserAuditService;
import com.tech.mkblogs.users.customspecification.UserCustomSpecification;
import com.tech.mkblogs.users.dto.ChangePasswordDTO;
import com.tech.mkblogs.users.dto.UserDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class UserService {

	@Autowired
	AccountUserRepository userRepository;
	
	@Autowired
	UserAuditService auditService;
	
	 public User findByUserName(String userName){
		 return userRepository.findByLoginName(userName);
	 }
	 
	 public Collection<User> findAllByLoginName(String userName) {
		 return userRepository.findAllByLoginName(userName);
	 }
	 
	 public void addUser(UserDTO userDTO) throws Exception {
		 User user = UserMapper.INSTANCE.toUser(userDTO);
		 user.setAccountExpired(false);
		 user.setAccountLocked(false);
		 user.setCredentialsExpired(false);
		 user.setEnabled(false);
		 if(userDTO.getAuthEncrypted()) {
			 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			 user.setPassword(encoder.encode(user.getPassword()));
		 }
		 Authorities authorities = new Authorities();
		 authorities.setAuthority(userDTO.getRole());
		 authorities.setUser(user);
		 Settings settings = user.getSettings();
		 settings.setUser(user);
		 user.addAuthorities(authorities);
		 user.setSettings(settings);
		 user.setCreatedName(auditService.getUserName());
		 userRepository.save(user);
	 }
	 
	 public void updateUser(UserDTO userDTO) throws Exception {
		 try {
			 Optional<User> optionalUser = userRepository.findById(userDTO.getId());
			 if(optionalUser.isPresent()) {
				 User user = optionalUser.get();
				 user.setLoginName(userDTO.getUsername());
				 //Authorities disabled as of now
				 /*
				 int counter = 0;
				 Set<Authorities> list = user.getAuthorities();
				 if (list.stream().anyMatch(authorities -> authorities.getAuthority() != userDTO.getRole())) {
					counter++;
				 }
				  
				 if(counter <= 0) {
					 Authorities authorities = new Authorities();
					 authorities.setAuthority(userDTO.getRole());
					 authorities.setUser(user);
					 user.addAuthorities(authorities);
				 }
				 */
				 user.setLastModifiedName(auditService.getUserName());
				 
				 Settings settings = user.getSettings();
				 settings.setConnectionType(userDTO.getConnectionType());
				 settings.setPrimaryKeyGenerationType(userDTO.getPrimaryKeyGenerationType());
				 settings.setAuthenticationType(userDTO.getAuthType());
				 settings.setAuthenticationEncrypted(userDTO.getAuthEncrypted());
				 user.setSettings(settings);
				 
				 userRepository.save(user);
			 }else {
				 throw new NoResultException("Entity Not Found " + userDTO.getId());
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
			 log.error(e.getMessage());
		 }
	 }
	 
	 public List<UserDTO> getUserSearch(UserDTO userDTO) throws Exception {
		 List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		 List<User> searchList = userRepository.findAll(new UserCustomSpecification(userDTO));
		 for(User user: searchList) {
			 UserDTO userDTOObject =  UserMapper.INSTANCE.toUserDTO(user);
			 userDTOList.add(userDTOObject);
		 }
		 return userDTOList;
	 }
	 
	 public User getUser(Integer userId) throws Exception {
		 Optional<User> optionalUser = userRepository.findById(userId);
		 if(optionalUser.isPresent()) {
			 return optionalUser.get();
		 }
		 return null;
	 }
	 
	 public UserDTO getUserDTO(Integer userId) throws Exception {
		 UserDTO userDTO = null;
		 Optional<User> optionalUser = userRepository.findById(userId);
		 if(optionalUser.isPresent()) {
			 User user = optionalUser.get();
			 userDTO =  UserMapper.INSTANCE.toUserDTO(user); 
		 }
		 return userDTO;
	 }
	 
	 public String deleteUser(Integer userId) throws Exception {
		 String status = "Failed";
		 try {
			 if(auditService.getUserId() == userId) {
				 status = "you can not delete your own account";
			 }else {
				 userRepository.deleteById(userId);
				 status = "Success";
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return status;
	 }
	 
	 public boolean passwordSameWithDB(ChangePasswordDTO passwordDTO) {
		 Optional<User> optionalUser = userRepository.findById(passwordDTO.getId());
		 boolean isMatched = false;
		 if(optionalUser.isPresent()) {
			 User user = optionalUser.get();
			 Boolean isEncypted = user.getSettings().getAuthenticationEncrypted();
			 String rawPassword = passwordDTO.getCurrentPassword();
			 String dbPassword = user.getPassword();
			 if(isEncypted) {
				 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				 isMatched = encoder.matches(rawPassword, dbPassword);
			 }else {
				 isMatched = (rawPassword.equalsIgnoreCase(dbPassword) ? true : false);
			 }
		 }else {
			 throw new NoResultException("No Records found for given "+passwordDTO.getId());
		 }
		 
		 return isMatched;
	 }
	 
	 public void updatePassword(ChangePasswordDTO passwordDTO) {
		 Optional<User> optionalUser = userRepository.findById(passwordDTO.getId());
		 if(optionalUser.isPresent()) {
			 User user = optionalUser.get();
			 Boolean isEncypted = user.getSettings().getAuthenticationEncrypted();
			 if(isEncypted) {
				 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				 user.setPassword(encoder.encode(passwordDTO.getPassword()));
			 }else {
				 user.setPassword(passwordDTO.getPassword());
			 }
			 userRepository.save(user);
		 }else {
			 throw new NoResultException("No Records found for given "+passwordDTO.getId());
		 }
	 }
}
