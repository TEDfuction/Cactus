package com.admin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin")
public class AdminVO implements Serializable{
	
	@Id
	@Column(name = "admin_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminId;
	
	@NotEmpty
	@Column(name = "admin_account", nullable = false)
	private String adminAccount;
	
	@NotEmpty
	@Column(name = "admin_password", nullable = false)
	private String adminPassword;
	
	@NotEmpty
	@Column(name = "admin_name", nullable = false)
	private String adminName;
	
	@NotNull
	@Column(name = "admin_status", nullable = false)
	private Integer adminStatus;

	
	//映射
	@OneToMany(mappedBy = "adminVO" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AdminAuthVO> adminAuthVO;
	
	
	
	
	public AdminVO() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Integer getAdminId() {
		return adminId;
	}




	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}




	public String getAdminAccount() {
		return adminAccount;
	}




	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}




	public String getAdminPassword() {
		return adminPassword;
	}




	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}




	public String getAdminName() {
		return adminName;
	}




	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}




	public Integer getAdminStatus() {
		return adminStatus;
	}




	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}




	public Set<AdminAuthVO> getAdminAuthVO() {
		return adminAuthVO;
	}




	public void setAdminAuthVO(Set<AdminAuthVO> adminAuthVO) {
		this.adminAuthVO = adminAuthVO;
	}
	
	
	
	
	
	
	
	
	
	
	
}
