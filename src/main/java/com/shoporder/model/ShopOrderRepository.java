package com.shoporder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ShopOrderRepository extends JpaRepository<ShopOrderVO, Integer> {
	
	@Query(value = "from ShopOrderVO where member_id=?1 order by product_order_date desc")
	List<ShopOrderVO> findByMemberId(Integer memberId);


	@Transactional
	@Modifying
	@Query(value = "delete from shop_order where shop_order_id =?1", nativeQuery = true)
	void deleteById(Integer shopOrderId);

}
