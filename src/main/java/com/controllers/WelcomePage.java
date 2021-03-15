package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.domain.Dto;
import com.domain.Users;

@Controller
public class WelcomePage {

	@RequestMapping("/")
	public String welcomePage(@ModelAttribute("userLogin")Users user ,Model model) {
		return "login";
	}

}
