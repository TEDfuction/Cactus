package com.shoporder.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.member.model.MemberVO;
import com.shopdiscountproject.model.ShopDiscountProjectVO;
import com.shoporderdetail.model.ShopOrderDetailVO;

@Entity
@Table(name = "shop_order")
public class ShopOrderVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_order_id")
	private Integer shopOrderId;
	
	@Column(name = "product_order_date"
			,insertable = false, updatable = false, nullable = false)
	private Timestamp productOrderDate;
	
	@Column(name = "product_amount", nullable = false)
	private Integer productAmount;
	
	@Column(name = "payment_method", nullable = false)
	private Integer paymentMethod;
	
	@Column(name = "shipping_method", nullable = false)
	private Integer shippingMethod;
	
	@Column(name = "order_status", nullable = false)
	private Integer orderStatus;
	
	
	
	
	
	
	//映射
	
	@OneToMany(mappedBy = "shopOrder" , cascade = CascadeType.ALL)
	private Set<ShopOrderDetailVO> shopOrderDetailVO;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")//name是我們的欄位，refer是我們參考別資料庫的欄位
	private MemberVO member;

//	@ManyToOne
//	@JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")//name是我們的欄位，refer是我們參考別資料庫的欄位
//	private ShopDiscountProjectVO shopDiscountProjectVO;

	public Integer getShopOrderId() {
		return shopOrderId;
	}

	public void setShopOrderId(Integer shopOrderId) {
		this.shopOrderId = shopOrderId;
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

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(Integer shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set<ShopOrderDetailVO> getShopOrderDetailVO() {
		return shopOrderDetailVO;
	}

	public void setShopOrderDetailVO(Set<ShopOrderDetailVO> shopOrderDetailVO) {
		this.shopOrderDetailVO = shopOrderDetailVO;
	}

	public MemberVO getMember() {
		return member;
	}

	public void setMember(MemberVO member) {
		this.member = member;
	}

//	public ShopDiscountProjectVO getShopDiscountProjectVO() {
//		return shopDiscountProjectVO;
//	}
//
//	public void setShopDiscountProjectVO(ShopDiscountProjectVO shopDiscountProjectVO) {
//		this.shopDiscountProjectVO = shopDiscountProjectVO;
//	}
	
	
	
	
	
}
