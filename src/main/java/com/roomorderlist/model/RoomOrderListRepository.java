package com.roomorderlist.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomOrderListRepository extends JpaRepository<RoomOrderListVO, Integer> {
	
	//家維新增 -- 會員專區用
	@Query(value="FROM RoomOrderListVO where room_order_id = ?1")
	public RoomOrderListVO findByRoomOrderId(Integer roomOrderId);
}
