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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "admin_auth")
public class AdminAuthVO implements Serializable{

	@Id
	@Column(name = "admin_auth_id",insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminAuthId;
	
	//映射
	@ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
	private AdminVO adminVO;
	
	@ManyToOne
    @JoinColumn(name = "admin_authorization_id", referencedColumnName = "admin_authorization_id")
	private AdminAuthorizationVO adminAuthorizationVO;

	
	public AdminAuthVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public Integer getAdminAuthId() {
		return adminAuthId;
	}





	public void setAdminAuthId(Integer adminAuthId) {
		this.adminAuthId = adminAuthId;
	}





	public AdminVO getAdminVO() {
		return adminVO;
	}


	public void setAdminVO(AdminVO adminVO) {
		this.adminVO = adminVO;
	}





	public AdminAuthorizationVO getAdminAuthorizationVO() {
		return adminAuthorizationVO;
	}





	public void setAdminAuthorizationVO(AdminAuthorizationVO adminAuthorizationVO) {
		this.adminAuthorizationVO = adminAuthorizationVO;
	}


	
	
	
}
