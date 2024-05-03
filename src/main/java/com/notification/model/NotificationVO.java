package com.notification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.member.model.MemberVO;

@Entity
@Table(name = "notification_list")
public class NotificationVO implements java.io.Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id",
			updatable = false, nullable = false)
	private Integer notiId;
		
	
	
	@Column(name = "notification_type",
			nullable = false)
	private Integer type;
	
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notification_time",
//			columnDefinition = "datetime",
			insertable = false, updatable = false, nullable = false)
	private Timestamp time;
	
	
	@Column(name = "notification_status",
			insertable = false, nullable = false)
	private Integer status;
	
	
	@Column(name = "notification_title",
			nullable = false)
	@NotEmpty(message="通知標題: 請勿空白")
	private String title;
	
	
	@Column(name = "notification_content",
			nullable = false)
	@NotEmpty(message="通知內容: 請勿空白")
	private String content;
	
	
	
	
	
	//Hibernate映射
	
	@ManyToOne
	@JoinColumn(name = "member_id" , referencedColumnName = "member_id")
	private MemberVO member;
	
	
	


	public NotificationVO() {
		super();
	}


	public Integer getNotiId() {
		return notiId;
	}


	public void setNotiId(Integer notiId) {
		this.notiId = notiId;
	}





	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}





	public Timestamp getTime() {
		return time;
	}


	public void setTime(Timestamp time) {
		this.time = time;
	}





	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}





	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}





	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}





	public MemberVO getMember() {
		return member;
	}


	public void setMember(MemberVO member) {
		this.member = member;
	}
}
