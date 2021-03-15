package com.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.OnlineExamApp;
import com.domain.Dto;
import com.domain.JsonResponse;
import com.domain.Module;
import com.domain.Role;
import com.domain.Users;
import com.repositories.ModuleRepo;
import com.repositories.RoleRepository;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/admin")
public class AdmainPageController {
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModuleRepo moduleRepo;
    @Autowired
    public JavaMailSender emailSender;

	@RequestMapping("/")
	public String openAdminPage( @ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		return "admin";
	}
	@RequestMapping("/addModule")
	public String addModule( @ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		return "addModule";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public String openAddNewUser(@ModelAttribute("addNewUser") Dto dto, BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		List<Role> list = ((List<Role>) roleRepo.findAll());
		// create a list to save roles;
		List<String> nameList = new ArrayList<>();
		// loop through each role in the database
		for (Role r : list) {
			if (!r.getRole().equals("ADMIN")) {
				nameList.add(r.getRole());
			}
		}
		model.addAttribute("userTypeValues", nameList);
		
		
		List<Module> listModule = ((List<Module>) moduleRepo.findAll());
		System.out.println(listModule);
		List<String> moduleList = new ArrayList<>();
		// loop through each module in the database
		for (Module m : listModule) {
			moduleList.add(m.getModuleCode());
		}
		model.addAttribute("ModuleCode", moduleList);
		
		return "createUser";
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String AddNewUser(@ModelAttribute("addNewUser") Dto dto, BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		List<Role> list = ((List<Role>) roleRepo.findAll());
		// create a list to save roles;
		List<String> nameList = new ArrayList<>();
		// loop through each role in the database
		for (Role r : list) {
			if (!r.getRole().equals("ADMIN")) {
				nameList.add(r.getRole());
			}
		}
		model.addAttribute("userTypeValues", nameList);
		
		
		List<Module> listModule = ((List<Module>) moduleRepo.findAll());
		System.out.println(listModule);
		List<String> moduleList = new ArrayList<>();
		// loop through each module in the database
		for (Module m : listModule) {
			moduleList.add(m.getModuleCode());
		}
		model.addAttribute("ModuleCode", moduleList);
		
		return "admin";
	}
	
//	@RequestMapping(value = "/createUser", params = "addUser", method = RequestMethod.POST)
//	public String addNewQuestion(@ModelAttribute("addNewUser") Dto dto, BindingResult result, Model model) {
//
//		return "admin";
//	}
	
	@RequestMapping(value = "/UserInfo", method = RequestMethod.POST)
	public	@ResponseBody String addNewModule(@ModelAttribute("addNewUser") Dto dto, BindingResult result, Model model) {
		String res = "Success";
			Users users = new Users();
			
			Users user = userRepo.findByEmail(dto.getEmail());
			if(user!=null) {
				return "Exist";
			}
			
			// set user details
			users.setEmail(dto.getEmail());
			users.setFirstName(dto.getFirstName());
			users.setLastName(dto.getLastName());

			// separate the list of module by comma
			List<String> arrayList = Arrays.asList(dto.getModule().split(","));
			Set<Module> moduleList = new HashSet<Module>();
			for (int i = 0; i < arrayList.size(); i++) {

				//check is the module enter is exist
				if (moduleRepo.findByModuleCode(arrayList.get(i)) != null) {
					Module module = moduleRepo.findByModuleCode(arrayList.get(i));
					//add all the modules into moduleList
					moduleList.add(module);
				}
			}
			// Set o List
			List<Module> moduleArrayList = new ArrayList<Module>(moduleList);
			
			// create and set Password
			String randomPass = users.generateRandomPassword();
			System.out.println(randomPass);
			
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			users.setPassword(pe.encode(randomPass));
			
			//send email
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo(dto.getEmail());
	        message.setFrom("kevin-zhang95@hotmail.com");
	        message.setSubject("Your password for the online exam"); 
	        message.setText(randomPass);
	        emailSender.send(message);
			

			// set user's roles
			String userRole = dto.getUserType();
			users.setRole(roleRepo.findByRole(userRole));

			users.setModuleList(moduleArrayList);
			userRepo.save(users);
		return res;
	}
	

}
