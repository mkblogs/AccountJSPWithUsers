package com.tech.mkblogs.users.controller;

import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tech.mkblogs.security.db.model.User;
import com.tech.mkblogs.users.dto.UserDTO;
import com.tech.mkblogs.users.service.UserService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/admin")
public class UserCotroller {

	@Autowired
	UserService userService;
	
	@GetMapping(value = "/users")
	public String create(Model model) throws Exception {
		log.info("| Request Time - Start - create() " + LocalTime.now());
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		return "users";
	}
	
	@PostMapping(value = "/users")
	public String saveUsers(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
			BindingResult result,Model model) throws Exception {
		log.info("| Request Time - Start - saveUsers() " + LocalTime.now());
		if(result.hasErrors()) {
			return "users";
		}else {
			userService.addUser(userDTO);
			return "usersuccess";
		}		
	}
	
	@GetMapping("/search")
	public String search(Model model) {
		log.info("| Request Time - Start - search() " + LocalTime.now());
		UserDTO userDTO = new UserDTO();
	    model.addAttribute("userDTO", userDTO); 
	    return "usersearch";
	}
	
	@PostMapping(value = "/search")
	public String searchUsers(@ModelAttribute UserDTO userDTO,BindingResult result,Model model) throws Exception {
		log.info("| Request Time - Start - searchUsers() " + LocalTime.now());
		List<UserDTO> userDTOList =  userService.getUserSearch(userDTO);
		model.addAttribute("userList", userDTOList);
	    return "usersearch";
	}
	
	@GetMapping(value = "/view")
	public String viewUserPage(Model model,@RequestParam("id") Integer userId) throws Exception {
		log.info("| Request Time - Start - viewUserPage() " + LocalTime.now());
		User user = userService.getUser(userId);
	    model.addAttribute("user", user); 
	    return "viewuser";
	}
	
	@DeleteMapping(value = "/delete",produces="text/plain")
	@ResponseBody
	public String delete(Model model,@RequestParam("id") Integer userId) throws Exception {
		log.info("| Request Time - Start - delete() " + LocalTime.now());
		String status = "";
	    status = userService.deleteUser(userId);
	    return status;
	}
	
	@GetMapping(value = "/edit")
	public String editUserPage(Model model,@RequestParam("id") Integer userId) throws Exception {
		log.info("| Request Time - Start - editUserPage() " + LocalTime.now());
		UserDTO userDTO = new UserDTO();
		userDTO = userService.getUserDTO(userId);		
	    model.addAttribute("userDTO", userDTO); 
	    return "edituser";
	}
	
	@PostMapping(value = "/edit")
	public String saveEditUserPage(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
			BindingResult result,Model model) throws Exception {
		log.info("| Request Time - Start - saveEditUserPage() " + LocalTime.now());
		if(result.hasErrors()) {
			return "edituser";
		}else {
			userService.updateUser(userDTO);
			model.addAttribute("userDTO", userDTO);
			return "usersuccess";
		}	    
	}
}
