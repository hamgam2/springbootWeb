package com.kh.finalproject.finalProject.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "WISH")
public class wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long no;

    @Column(name = "MEM_CAT")
    private String memCat;

    @Column(name = "MEM_NUM")
    private Long memNum;
    
    @Column(name = "MEM_img")
    private String memImg;

    @Column(name = "WISH_NAME")
    private String wishName;
    
    @Column(name = "COUNT")
    private int count;

    @Column(name = "WISH_FUN")
    private String wishFun;

    @Column(name = "END_DATE")
    private String endDate;
    
	@Column(name = "MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	@Column(name = "REMAIN_DATE")
	private int remainDate;
    
}
