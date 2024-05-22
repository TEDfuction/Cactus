package com.productcategory.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productCategoryService")
public class ProductCategoryService {

	@Autowired
	ProductCategoryRepository repository;

	public void addProductCategory(ProductCategoryVO productCategoryVO) {
		repository.save(productCategoryVO);
	}

	public void updateProductCategory(ProductCategoryVO productCategoryVO) {
		repository.save(productCategoryVO);
	}

//	public void deleteProductCategory(Integer deptno) {
//		if (repository.existsById(deptno))
//			repository.deleteById(deptno);
//	}


	public ProductCategoryVO getOneProductCategory(Integer productCategoryId) {
		Optional<ProductCategoryVO> optional = repository.findById(productCategoryId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ProductCategoryVO> getAll() {
		return repository.findAll();
	}

//	public Set<ProductCategoryVO> getEmpsByDeptno(Integer deptno) {
//		return getOneDept(deptno).getEmps();
//	}

}
