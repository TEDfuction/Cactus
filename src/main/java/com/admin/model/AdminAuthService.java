package com.admin.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthService {

	@Autowired
	AdminAuthRepository repository;
	
	public void addAdminAuth(AdminAuthVO adminAuthVO) {
		repository.save(adminAuthVO);
	}
	
	public void updateAdminAuth(AdminAuthVO adminAuthVO) {
		repository.save(adminAuthVO);
	}
	
	public List<AdminAuthVO> getAll(){
		return repository.findAll();
	}
	
	public List<AdminAuthVO> findByAdminId(Integer adminId){
		return repository.findByAdminId(adminId);
	}
	
	public List<AdminAuthVO> findByAdminAuthorizationId(Integer adminAuthorizationId){
		return repository.findByAdminAuthorizationId(adminAuthorizationId);
	}
	
	public void emptyAuth(Integer adminId) {
		repository.emptyAuth(adminId);
	}
}
