package com.room.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomVO, Integer> {

    @Query(value = "from  RoomVO where roomTypeVO.roomTypeId=?1")
    List<RoomVO> findByRoomTypeId(Integer roomTypeId);


    public RoomVO findByRoomPrice(Integer roomPrice);
}
