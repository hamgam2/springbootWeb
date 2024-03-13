package com.kh.finalproject.finalProject.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.finalProject.entity.category;
import com.kh.finalproject.finalProject.entity.kit;
import com.kh.finalproject.finalProject.entity.wish;
import com.kh.finalproject.finalProject.repository.categoryRepository;
import com.kh.finalproject.finalProject.repository.kitRepository;
import com.kh.finalproject.finalProject.repository.wishRepository;
import com.kh.finalproject.finalProject.service.ApiService;
import com.kh.finalproject.finalProject.vo.ItemData;
import com.kh.finalproject.finalProject.vo.info2item;
import com.kh.finalproject.finalProject.vo.infoitem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class mysearchController {

	@Autowired
	private wishRepository wishrepository;
	
	@Autowired
	private kitRepository kitrepository;
	
	@Autowired
    private ApiService apiService;
	
	@Autowired
    private categoryRepository categoryrepository;
	
	
	@GetMapping("/")
	public String main(Model model) {
		
		List<wish> wishlist=wishrepository.findAll();
		List<kit> kitlist=kitrepository.findAll();
		List<category> buttonlist=categoryrepository.findAll();
		
		log.info("wishlist {}",wishlist);
		log.info("kitlist {}",kitlist);
		
		Long wishcount=wishrepository.count();
		Long kitcount=kitrepository.count();
		
		model.addAttribute("wishcount", wishcount);
		model.addAttribute("kitcount", kitcount);
		
		model.addAttribute("wishlist", wishlist);
		model.addAttribute("kitlist", kitlist);
		
		model.addAttribute("buttonlist",buttonlist);
		return "main";
	}
	
}
