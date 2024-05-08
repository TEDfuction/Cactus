package com.product.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl {

	
	@Autowired
	ProductRepository repositroy;
	
	public List<ProductVO> getAll(){
		return repositroy.findAll();
	}
}
