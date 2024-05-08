package com.shoporderdetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.product.model.ProductVO;
import com.shoporder.model.ShopOrderVO;

@Entity
@Table(name = "shop_order_detail")
public class ShopOrderDetailVO implements java.io.Serializable{
	
	@Id
	@Column(name = "shop_order_detail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shopOrderDeatilId;
	

	@Column(name = "order_quantity", nullable = false)
	private Integer orderQuantity;
	
	@Column(name = "product_amount", nullable = false)
	private Integer productAmount;
	
	
	
	//映射
	
	@ManyToOne
	@JoinColumn(name = "shop_order_id", referencedColumnName = "shop_order_id")
	private ShopOrderVO shopOrder;
	
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private ProductVO product;

	
	
	
	
	public Integer getShopOrderDeatilId() {
		return shopOrderDeatilId;
	}

	public void setShopOrderDeatilId(Integer shopOrderDeatilId) {
		this.shopOrderDeatilId = shopOrderDeatilId;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}

	public ShopOrderVO getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(ShopOrderVO shopOrder) {
		this.shopOrder = shopOrder;
	}

	public ProductVO getProduct() {
		return product;
	}

	public void setProduct(ProductVO product) {
		this.product = product;
	}
	
	
	
}
