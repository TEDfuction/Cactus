package com.roomschedule.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用JPA完成資料庫增刪改查
 * JpaRepository<T, ID>
 * T:表格VO
 * ID:對應主鍵的類型
 * */
@Repository
public interface RoomScheduleRepository extends JpaRepository<RoomScheduleVO, Integer> {

    @Query(value = "from RoomScheduleVO where roomTypeVO.roomTypeId=?1")
    RoomScheduleVO getByRoomTypeId(Integer roomTypeId);

}
