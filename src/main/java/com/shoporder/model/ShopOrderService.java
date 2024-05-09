package com.shoporder.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopOrderService {
	
	@Autowired
	ShopOrderRepository repository;
	
	public void addOrder(ShopOrderVO shopOrderVO) {
		repository.save(shopOrderVO);
	}
	
	public void updateOrder(ShopOrderVO shopOrderVO) {
		repository.save(shopOrderVO);
	}
	
	public List<ShopOrderVO> getAll() {
		return repository.findAll();
	}
	
	public void deleteOrder(Integer shopOrderId) {
		repository.deleteById(shopOrderId);
	}
	
    public Optional<ShopOrderVO> findById(Integer orderId) {
    	return repository.findById(orderId); 
    }

	
	public List<ShopOrderVO> findByMemberId(Integer memberId){
		return repository.findByMemberId(memberId);
	}
}
