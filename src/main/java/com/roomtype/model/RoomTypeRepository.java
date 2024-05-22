package com.roomtype.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 使用JPA完成資料庫增刪改查
 * JpaRepository<T, ID>
 * T:表格VO
 * ID:對應主鍵的類型
 * */
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeVO, Integer> {

    @Query(value = "SELECT rt.ROOM_TYPE_NAME, r.ROOM_TYPE_ID, r.ROOM_PRICE, r.ROOM_GUEST_AMOUNT " +
            "FROM ROOM_TYPE rt " +
            "LEFT JOIN ROOM r ON rt.ROOM_TYPE_ID = r.ROOM_TYPE_ID " +
            "WHERE rt.ROOM_TYPE_STATUS = TRUE " +
            "AND rt.ROOM_TYPE_NAME = :roomTypeName " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 FROM ROOM_SCHEDULE rs " +
            "    WHERE rs.ROOM_TYPE_ID = rt.ROOM_TYPE_ID " +
            "    AND rs.ROOM_SCHEDULE_DATE BETWEEN :checkInDate AND :checkOutDate" +
            "    AND (:roomGuestAmount IS NULL OR rs.ROOM_GUEST_AMOUNT <= :roomGuestAmount)" +
            ") " +
            "AND (:roomGuestAmount IS NULL OR r.ROOM_GUEST_AMOUNT <= :roomGuestAmount)", nativeQuery = true)
    List<Object[]> findAvailableRoomTypes(@Param("roomTypeName") String roomTypeName,
                                          @Param("checkInDate") Date checkInDate,
                                          @Param("checkOutDate") Date checkOutDate,
                                          @Param("roomGuestAmount") Integer roomGuestAmount);

    Optional<RoomTypeVO> findByRoomTypeName(@Param("roomTypeName") String roomTypeName);
}
