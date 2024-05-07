package com.shoporder.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.productcategory.model.ProductCategoryVO;
import com.shopdiscountproject.model.ShopDiscountProjectVO;

@Entity
@Table(name = "shop_order")
public class ShopOrderVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_order_id")
	private Integer shopOrderID;
	
	//還未有MemberVO所以不能執行
//	@ManyToOne
//	@JoinColumn(name = "member_id", referencedColumnName = "member_id")//name是我們的欄位，refer是我們參考別資料庫的欄位
//	private Member member;
	
	@Column(name = "member_id")
	private Integer memberID;
	
	@ManyToOne
	@JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")//name是我們的欄位，refer是我們參考別資料庫的欄位
	private ShopDiscountProjectVO shopDiscountProjectVO;
	//	@Column(name = "promotion_id")
//	private Integer promotionID;
	
	@Column(name = "product_discount")
	private Integer productDiscount;
	
	@Column(name = "product_order_date", nullable = false)
	private Timestamp productOrderDate;
	
	@Column(name = "product_amount", nullable = false)
	private Integer productAmount;
	
	
	@Column(name = "shipping_method", nullable = false)
	private Boolean shippingMethod;
	
	@Column(name = "order_status", nullable = false)
	private Integer orderStatus;
	
//	private String orderName;
//	
//	private String orderMobile;
//	
//	private String orderAddress;

	
	public Integer getShopOrderID() {
		return shopOrderID;
	}

	public void setShopOrderID(Integer shopOrderID) {
		this.shopOrderID = shopOrderID;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	public ShopDiscountProjectVO getShopDiscountProjectVO() {
		return shopDiscountProjectVO;
	}

	public void setShopDiscountProjectVO(ShopDiscountProjectVO shopDiscountProjectVO) {
		this.shopDiscountProjectVO = shopDiscountProjectVO;
	}

	public Integer getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(Integer productDiscount) {
		this.productDiscount = productDiscount;
	}

	public Timestamp getProductOrderDate() {
		return productOrderDate;
	}

	public void setProductOrderDate(Timestamp productOrderDate) {
		this.productOrderDate = productOrderDate;
	}

	public Integer getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}

	public Boolean getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(Boolean shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
