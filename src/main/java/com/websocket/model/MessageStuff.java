package com.websocket.model;

public class MessageStuff {
	
	//供WebSocket及JSON傳遞資料用之DTO
	
	private String action;
	private String memberId;
	private String notificationId;
	
	
	public MessageStuff(String action, String memberId, String notificationId) {
		super();
		this.action = action;
		this.memberId = memberId;
		this.notificationId = notificationId;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getNotificationId() {
		return notificationId;
	}


	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	
	
	
	
}