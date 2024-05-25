package com.notification.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<NotificationVO, Integer> {
	
	@Query(value = "from NotificationVO where member_id=?1 order by time desc")
	List<NotificationVO> findByMemberIdByOrderByTimeDesc(Integer memberId);
	
	
	//更改訊息讀取狀態,供WebSocket調用
	@Transactional
	@Modifying
	@Query(value = "update NotificationVO n set n.status=1 where n.notiId=?1")
	void changeStatus(Integer notiId);
	
	//更改排序
	@Modifying
	List<NotificationVO> getAllByOrderByTimeDesc();
	
	
	//搜尋未讀通知之個數,供WebSocket調用
	@Query(value = "select count(*) from notification_list where member_id=?1 and notification_status=0",nativeQuery=true)
	Integer getNotiUnread(Integer memberId);
}
