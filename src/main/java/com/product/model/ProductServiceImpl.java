package com.product.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductServiceImpl {

	
	@Autowired
	ProductRepository repository;
	
	public List<ProductVO> getAll(){
		return repository.findAll();
	}
	
	public ProductVO findById(Integer productId) {
		Optional<ProductVO> optional = repository.findById(productId);
		return optional.orElse(null);
	}
}
