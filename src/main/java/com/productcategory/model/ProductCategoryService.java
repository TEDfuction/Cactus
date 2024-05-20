package com.productcategory.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryService {
	
	@Autowired
	ProductCategoryRepository repository;
	
	public List<ProductCategoryVO> getAll(){
		return repository.findAll();
	}

}
