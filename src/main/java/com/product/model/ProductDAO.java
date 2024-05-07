package com.product.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.util.HibernateUtil;

public class ProductDAO implements ProductDAO_interface{
	
	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
		private SessionFactory factory;

		public ProductDAO() {
			factory = HibernateUtil.getSessionFactory();
		}
		
		// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
		// 以避免請求執行緒共用了同個 Session
		private Session getSession() {
			return factory.getCurrentSession();
		}

	
	@Override
	public void insert(ProductVO productVO) {

		try {
			getSession().save(productVO);
//			return id;
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	
	
	@Override
	public void update(ProductVO productVO) {
		try {
			getSession().update(productVO);
//			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return -1;
	}
	
	
	@Override
	public ProductVO findByPrimaryKey(Integer product_id) {
		try {
			ProductVO productVO = getSession().get(ProductVO.class, product_id);
			return productVO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void delete(Integer product_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductVO> getAll() {
		
		return getSession().createQuery("from ProductVO", ProductVO.class).list();
//		try {
//			List<ProductVO> list1 = getSession().createQuery("from ProductVO", ProductVO.class).list();
//			return list1;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
	}	
		
	}
	
