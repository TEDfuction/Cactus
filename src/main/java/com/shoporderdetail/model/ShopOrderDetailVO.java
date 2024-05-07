package com.shoporderdetail.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.shoporderdetail.model.ShopOrderDetailVO.CompositeDetail;

@Entity
@Table(name = "shop_order_detail")
@IdClass(CompositeDetail.class)
public class ShopOrderDetailVO {
	
	@Id
	@Column(name = "shop_order_id" , updatable = false)
	private Integer shopOrderID;
	
	@Id
	@Column(name = "product_id", updatable = false)
	private Integer productID;
	
	@Column(name = "order_quantity", nullable = false)
	private Integer orderQuantity;
	
	@Column(name = "product_amount", nullable = false)
	private Integer productAmount;
	
	
	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail getCompositeKey() {
		return new CompositeDetail(shopOrderID, productID);
	}

	public void setCompositeKey(CompositeDetail key) {
		this.shopOrderID = key.getShopOrderID();
		this.productID = key.getProductID();
	}
	
	
	public Integer getShopOrderID() {
		return shopOrderID;
	}

	public void setShopOrderID(Integer shopOrderID) {
		this.shopOrderID = shopOrderID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
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






	static class CompositeDetail implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private Integer shopOrderID;
		private Integer productID;
		
		public CompositeDetail() {
			super();
		}
		
		public CompositeDetail(Integer shopOrderID, Integer productID) { 
			super();
			this.shopOrderID = shopOrderID;
			this.productID = productID;
		}

		public Integer getShopOrderID() {
			return shopOrderID;
		}

		public void setShopOrderID(Integer shopOrderID) {
			this.shopOrderID = shopOrderID;
		}

		public Integer getProductID() {
			return productID;
		}

		public void setProductID(Integer productID) {
			this.productID = productID;
		}

		@Override
		public int hashCode() {
			return Objects.hash(productID, shopOrderID);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail other = (CompositeDetail) obj;
			return Objects.equals(productID, other.productID) && Objects.equals(shopOrderID, other.shopOrderID);
		}		

	}
}
