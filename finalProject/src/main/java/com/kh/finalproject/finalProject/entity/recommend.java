package com.kh.finalproject.finalProject.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "RECOMMEND")
public class recommend {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "NO")
	    private Long no;

	    @Column(name = "RECOMMENDCATEGORY_NAME")
	    private String recommendcategoryName;
	    
	    @Column(name = "RECOMMEND_NAME")
	    private String recommendName;
	    
	    @Column(name = "RECOMMEND_IMG")
	    private String recommendImg;
	    
	    
}
