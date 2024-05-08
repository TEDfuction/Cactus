package com.shoporderdetail.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ShopOrderDetailService {

	@Autowired
	ShopOrderDetailRepository repository;
	
	public List<ShopOrderDetailVO> findByShopOrderId(Integer shopOrderId){
		return repository.findByShopOrderId(shopOrderId);
	}
}
