package com.kh.finalproject.finalProject.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class selectItem {

	
	private Date endDate;
	private int count;
	private String memImg;
	private String wishName;
	private String wishFun;
	
}
