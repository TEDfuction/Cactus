package com.admin.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AdminAuthorizationService {
	
	@Autowired
	AdminAuthorizationRepository repository;
	
	public List<AdminAuthorizationVO> getAll(){
		return repository.findAll();
	}

}
