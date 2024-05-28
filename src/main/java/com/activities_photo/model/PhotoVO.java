package com.activities_photo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.activities_item.model.ItemVO;

@Entity
@Table(name = "activity_photo")
public class PhotoVO implements Serializable{

	
	private static final long serialVersionUID = 5741389575757322797L;
	
	public PhotoVO() {
	}
	
	@Id
	@Column(name = "activity_photo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer activityPhotoId;
	
	
	//fk多對一 referencedColumnName join activity的activity_category_id欄位
	@ManyToOne
	@JoinColumn(name = "activity_id", referencedColumnName ="activity_id")
	private ItemVO activityPh;

	public ItemVO getActivityPh() {
		return activityPh;
	}



	public void setActivityPh(ItemVO activityPh) {
		this.activityPh = activityPh;
	}

	@Column(name = "activity_photo", columnDefinition = "longblob")
	private byte[] activityPhoto;



	public Integer getActivityPhotoId() {
		return activityPhotoId;
	}



	public void setActivityPhotoId(Integer activityPhotoId) {
		this.activityPhotoId = activityPhotoId;
	}



	public byte[] getActivityPhoto() {
		return activityPhoto;
	}



	public void setActivityPhoto(byte[] activityPhoto) {
		this.activityPhoto = activityPhoto;
	}
	
	
	

	
	
}