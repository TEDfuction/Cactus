package com.shoporderdetail.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ShopOrderDetailService {

	@Autowired
	ShopOrderDetailRepository repository;
	
	public void addShopOrderDetail(ShopOrderDetailVO shopOrderDetail) {
		repository.save(shopOrderDetail);
	}
	
	public List<ShopOrderDetailVO> findByShopOrderId(Integer shopOrderId){
		return repository.findByShopOrderId(shopOrderId);
	}
}
