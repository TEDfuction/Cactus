package com.product.model;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productcategory.model.ProductCategoryVO;

import CompositeQuery.HibernateUtil_CompositeQuery_Product3;

@Service("productService")
public class ProductService {
	
	@Autowired
	ProductRepository repository;
	
	//很像是複合查詢用的
	@Autowired
    private SessionFactory sessionFactory;
	
//	private ProductDAO_interface dao;
	
//	public ProductService() {
//		dao = new ProductDAO();
//	}
	
	public void addProduct(ProductVO productVO) {
		repository.save(productVO);
	}
	
	
	public void updateProduct(ProductVO productVO) {
		repository.save(productVO);
	}
	
	
	public ProductVO getOneProduct(Integer productId){
		Optional<ProductVO> optional = repository.findById(productId);
		return optional.orElse(null);
	}
	
//	public List<ProductVO> getAll(){
//		return dao.getAll();
//	}
	
	public List<ProductVO> getAll() {
		return repository.findAll();
	}
	
	public List<ProductVO> getAll(Map<String, String[]> map) {
		return HibernateUtil_CompositeQuery_Product3.getAllC(map,sessionFactory.openSession());//關鍵行
	}
	
	public ProductVO findById(Integer productId) {
		Optional<ProductVO> optional = repository.findById(productId);
		return optional.orElse(null);
		
	}
}
