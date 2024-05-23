package com.admin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "admin_authorization")
public class AdminAuthorizationVO implements Serializable{
	
	@Id
	@Column(name = "admin_authorization_id",insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminAuthorizationId;
	
	
	@Column(name = "admin_authorization_name",insertable = false, updatable = false, nullable = false)
	private String adminAuthorizationName;
	
	
	//映射
	@OneToMany(mappedBy = "adminAuthorizationVO" , cascade = CascadeType.ALL)
	private Set<AdminAuthVO> adminAuthVO;
	
	
	
	public AdminAuthorizationVO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getAdminAuthorizationId() {
		return adminAuthorizationId;
	}



	public void setAdminAuthorizationId(Integer adminAuthorizationId) {
		this.adminAuthorizationId = adminAuthorizationId;
	}



	public String getAdminAuthorizationName() {
		return adminAuthorizationName;
	}



	public void setAdminAuthorizationName(String adminAuthorizationName) {
		this.adminAuthorizationName = adminAuthorizationName;
	}



	public Set<AdminAuthVO> getAdminAuthVO() {
		return adminAuthVO;
	}



	public void setAdminAuthVO(Set<AdminAuthVO> adminAuthVO) {
		this.adminAuthVO = adminAuthVO;
	}

}
