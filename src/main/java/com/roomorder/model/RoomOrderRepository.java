package com.roomorder.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 使用JPA完成資料庫增刪改查
 * JpaRepository<T, ID>
 * T:表格VO
 * ID:對應主鍵的類型
 * */
@Repository
public interface RoomOrderRepository extends JpaRepository<RoomOrderVO, Integer> {

    @Query(value = "from  RoomOrderVO where memberId.memberId=?1")
    List<RoomOrderVO> findByMemberId(Integer memberId);

//    List<RoomOrderVO> findByCheckInDate(Date checkInDate);


    @Modifying
    @Query(value = "from RoomOrderVO where checkInDate between :start and :end")
    List<RoomOrderVO> findByCheckInDate(@Param("start") LocalDate start, @Param("end") LocalDate end);



    @Modifying
    @Query(value = "from RoomOrderVO where roomOrderDate between :ROstart and :ROend")
    List<RoomOrderVO>findByRoomOrderDate(@Param("ROstart") LocalDate ROstart, @Param("ROend") LocalDate ROend);

}