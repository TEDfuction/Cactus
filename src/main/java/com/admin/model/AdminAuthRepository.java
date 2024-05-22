package com.admin.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminAuthRepository extends JpaRepository<AdminAuthVO, Integer>{

	
	@Query(value = "from AdminAuthVO where admin_id=?1")
	public List<AdminAuthVO> findByAdminId(Integer adminId);
	
	@Query(value = "from AdminAuthVO where admin_authorization_id=?1")
	public List<AdminAuthVO> findByAdminAuthorizationId(Integer adminAuthorizationId);

	@Transactional
	@Modifying
	@Query(value = "delete from AdminAuthVO where admin_id=?1")
	public void emptyAuth(Integer adminId);
	


}
