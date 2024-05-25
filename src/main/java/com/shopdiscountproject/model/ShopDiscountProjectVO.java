package com.shopdiscountproject.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shop_discount_project")
public class ShopDiscountProjectVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "promotion_id")
	private Integer promotionId;
	
	@Column(name = "promotion_title", nullable = false)
	private String promotionTitle;
	
	@Column(name = "promotion_content", nullable = false)
	private String promotionContent;
	
	@Column(name = "promotion_state", nullable = false)
	private Boolean promotionState;
	
	@Column(name = "promotion_coupon", nullable = false)
	private Integer promotionCoupon;
	
	@Column(name = "promotion_started", nullable = false)
	private Timestamp promotionStarted;
	
	@Column(name = "promotion_end", nullable = false)
	private Timestamp promotionEnd;

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public String getPromotionContent() {
		return promotionContent;
	}

	public void setPromotionContent(String promotionContent) {
		this.promotionContent = promotionContent;
	}

	public Boolean getPromotionState() {
		return promotionState;
	}

	public void setPromotionState(Boolean promotionState) {
		this.promotionState = promotionState;
	}

	public Integer getPromotionCoupon() {
		return promotionCoupon;
	}

	public void setPromotionCoupon(Integer promotionCoupon) {
		this.promotionCoupon = promotionCoupon;
	}

	public Timestamp getPromotionStarted() {
		return promotionStarted;
	}

	public void setPromotionStarted(Timestamp promotionStarted) {
		this.promotionStarted = promotionStarted;
	}

	public Timestamp getPromotionEnd() {
		return promotionEnd;
	}

	public void setPromotionEnd(Timestamp promotionEnd) {
		this.promotionEnd = promotionEnd;
	}
	
	
}
