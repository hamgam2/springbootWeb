package com.kh.finalproject.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.finalproject.finalProject.repository.wishRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class testController {
	
	@Autowired
	private wishRepository wishrepository;
	
	@GetMapping("/home")
	public String home() {
		
		
		
		return "home";
	}
}
