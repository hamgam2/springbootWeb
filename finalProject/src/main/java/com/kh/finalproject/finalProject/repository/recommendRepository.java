package com.kh.finalproject.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.finalProject.entity.recommend;

@Repository
public interface recommendRepository extends JpaRepository<recommend, Long>{

}
