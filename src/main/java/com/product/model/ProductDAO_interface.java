package com.product.model;

import java.util.List;

public interface ProductDAO_interface {
	public void insert(ProductVO productVO);
	public void update(ProductVO productVO);
	public void delete(Integer product_id);
	public ProductVO findByPrimaryKey(Integer product_id);
	public List<ProductVO> getAll();
	
}
