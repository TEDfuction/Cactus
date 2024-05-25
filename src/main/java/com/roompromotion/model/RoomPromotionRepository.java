package com.roompromotion.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomPromotionRepository extends JpaRepository<RoomPromotionVO, Integer> {

    @Query(value = "SELECT rp.promotion_title FROM room_promotion rp\n" +
            "WHERE :selectCheckIn BETWEEN rp.promotion_started AND rp.promotion_end;", nativeQuery = true)
    List<String> findBySelectCheckIn(@Param("selectCheckIn") LocalDate selectCheckIn);
}
