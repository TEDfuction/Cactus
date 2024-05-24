package com.admin.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminService {

	@Autowired
	AdminRepository repository;
	
	public void addAdmin(AdminVO adminVO) {
		repository.save(adminVO);
	}
	
	public void updateAdmin(AdminVO adminVO) {
		repository.save(adminVO);
	}
	
	public List<AdminVO> getAll(){
		return repository.findAll();
	}
	
	public Optional<AdminVO> findById(Integer adminId) {
		return repository.findById(adminId);
	}
	
	public AdminVO findByAccount(String adminAccount) {
		return repository.findByAccount(adminAccount);
	}
}
