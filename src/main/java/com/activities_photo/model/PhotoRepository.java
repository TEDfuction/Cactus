package com.activities_photo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
public interface PhotoRepository extends JpaRepository<PhotoVO, Integer> {
	        //建立交易
			@Transactional
			//修改操作
			@Modifying
			//刪除特定活動ID
			@Query(value = "delete from activity_photo where activity_photo_id = ?1", nativeQuery = true)
			void deleteByActivityId(int activityId);




			
			
			
			
			
	

}
