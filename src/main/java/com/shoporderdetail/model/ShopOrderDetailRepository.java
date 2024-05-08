package com.shoporderdetail.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopOrderDetailRepository extends JpaRepository<ShopOrderDetailVO, Integer>{

		@Query(value = "from ShopOrderDetailVO where shop_order_id=?1")
		public List<ShopOrderDetailVO> findByShopOrderId(Integer shopOrderId);
}
