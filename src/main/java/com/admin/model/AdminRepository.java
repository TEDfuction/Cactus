package com.admin.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<AdminVO, Integer>{
	
	@Query(value = "from AdminVO where admin_account=?1")
	public AdminVO findByAccount(String adminAccount);
	
}
