package com.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;

import com.OnlineExamApp;
import com.domain.Dto;
import com.domain.JsonResponse;
import com.domain.Users;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired UserRepo userRepo;
	
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	public String openLoginPage(@ModelAttribute("userLogin") Users user, Model model, Principal Users ) {
		
		// if user already logged in they need to logout first before the next login 
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		if(Users != null) {
			return "addusererror";
		}
		
		return "login";
		
	}
	
	@RequestMapping(value ="/Reset", method = RequestMethod.GET)
	public String openResetPage(@ModelAttribute("dto") Dto dto) {
		
		return "resetPassword";
	}

	@RequestMapping(value = "/Reset/Password", method = RequestMethod.POST)
	public @ResponseBody String ResetPassPage(@ModelAttribute("dto") Dto dto) {
		Users user = userRepo.findByEmail(dto.getEmail());

		if (user != null) {
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			
			if (pe.matches(dto.getOldPassword(), user.getPassword())) {
				if (dto.getPassword().equals(dto.getPassword1())) {
					user.setPassword(pe.encode(dto.getPassword()));
					userRepo.save(user);
					return "Change success";
				} else {
					return "Different";
				}
			} else {
				return "oldPass incorrect";
			}
		}

		return "User not exist";
	} 
	
	@RequestMapping(value="/loginValidate", method = RequestMethod.POST)
	public  @ResponseBody JsonResponse validateLogin(@ModelAttribute("userLogin") Users user, BindingResult result, Model model) {
        JsonResponse res = new JsonResponse();
      
   	 	BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
   	 	
   	 	Users u;
   	 	//user.getEmail is getting the email as an id
		u = userRepo.findByEmail(user.getEmail());

   	 	if(u != null) {
   	 		if(!pe.matches(user.getPassword(), u.getPassword()) && !user.getPassword().isEmpty()) {
   	   	 		result.reject("password is incorrect");
   	   	 	}
   		   	if(user.getPassword().isEmpty()) {
   	   	 		result.reject("Please enter your password");
   	   	}
   	 	}
   	 	else {
   	 		result.reject("This email is not exist, please contact admin.");
   	 	}
   	 	if(user.getEmail().isEmpty()) {
   	 		result.reject("Please enter an email");
   	 	}

   	 	
		if(!result.hasErrors()) {
			res.setStatus("SUCCESS");

		}
		else{
			res.setStatus("FAIL");
			res.setResult(result.getAllErrors());
			}

		return res;
	}
    @RequestMapping(value = "/success-login", method = RequestMethod.GET)
    public String authenticate() {

    		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		authUser.getAuthorities().stream().forEach(c -> System.out.println (c));

		System.out.println("logging in as " + authUser.getUsername());
		Users user = userRepo.findByEmail(authUser.getUsername());
        String view;
        switch (user.getRole().getId()) {
        	case OnlineExamApp.ADMIN: 
        		view = "redirect:/admin/"; 
        		break;
        	case OnlineExamApp.TEACHER: 
        		view = "redirect:/teacher/"; 
        		break;
        	case OnlineExamApp.STUDENT: 
        		view = "redirect:/student/"; 
        		break;		
        default: //	case VoteApplication.USER: 
        		view = "welcome"; 
        		break;
        		
        }
		
		return view;
		}
    
    @RequestMapping("/access-denied")
    public String accessDenied() {
    	return "login";
    }
}
