package com.productcategory.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.product.model.ProductVO;

@Entity
@Table(name="product_category")
public class ProductCategoryVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_category_id")
	private Integer productCategoryId;
	
	@OneToMany(mappedBy = "productCategoryVOs")
	private Set<ProductVO> productVOs;
	
	@Column(name = "product_category_name", nullable = false)
	private String productCategoryName;
	
	
	public Integer getProductCategotyId() {
		return productCategoryId;
	}

	public void setProductCategotyId(Integer productCategotyId) {
		this.productCategoryId = productCategotyId;
	}
	
	
	public Set<ProductVO> getProductVOs() {
		return productVOs;
	}

	public void setProductVOs(Set<ProductVO> productVOs) {
		this.productVOs = productVOs;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
}
