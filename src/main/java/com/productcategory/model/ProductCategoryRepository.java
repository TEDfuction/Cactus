package com.productcategory.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryVO,Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from cactus.product_category where product_category_id =?1", nativeQuery = true)
	void deleteByProductCategory(int productCategoryId);
}