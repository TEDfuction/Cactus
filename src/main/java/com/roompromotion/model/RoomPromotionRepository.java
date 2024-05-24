package com.roompromotion.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomPromotionRepository extends JpaRepository<RoomPromotionVO, Integer> {

    @Query(value = "SELECT * FROM room_promotion rp " +
            "WHERE :selectCheckIn BETWEEN rp.promotion_started AND rp.promotion_end " +
            "AND rp.promotion_state = 1", nativeQuery = true)
    List<RoomPromotionVO> findBySelectCheckIn(@Param("selectCheckIn") LocalDate selectCheckIn);
}

