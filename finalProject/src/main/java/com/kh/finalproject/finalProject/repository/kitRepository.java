package com.kh.finalproject.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.kh.finalproject.finalProject.entity.kit;

@Repository
public interface kitRepository extends JpaRepository<kit, Long> {

}
