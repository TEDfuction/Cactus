package com.roomtypepic.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 使用JPA完成資料庫增刪改查
 * JpaRepository<T, ID>
 * T:表格VO
 * ID:對應主鍵的類型
 * */
@Repository
public interface RoomTypePicRepository extends JpaRepository<RoomTypePicVO, Integer> {

    @Query(value = "from  RoomTypePicVO where roomTypeVO.roomTypeId=?1")
    Optional<RoomTypePicVO> findByRoomTypeId(Integer roomTypeId);
}