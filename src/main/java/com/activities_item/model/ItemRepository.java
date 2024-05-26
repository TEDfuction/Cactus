package com.activities_item.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
public interface ItemRepository extends JpaRepository<ItemVO, Integer>{
	    //建立交易
		@Transactional
		//修改操作
		@Modifying
		//刪除特定活動ID
		@Query(value = "delete from activity_item where activity_id = ?1", nativeQuery = true)
		void deleteByActivityId(int activityId);

}
