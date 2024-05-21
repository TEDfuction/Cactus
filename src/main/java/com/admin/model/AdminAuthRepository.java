package com.admin.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminAuthRepository extends JpaRepository<AdminAuthVO, Integer>{

	
	@Query(value = "from AdminAuthVO where admin_id=?1")
	public List<AdminAuthorizationVO> findByAdminId(Integer adminId);
	
	@Query(value = "from AdminAuthVO where admin_authorization_id=?1")
	public List<AdminVO> findByAdminAuthorizationId(Integer adminAuthorizationId);
}
