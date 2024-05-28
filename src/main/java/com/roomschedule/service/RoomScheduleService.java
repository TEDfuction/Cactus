package com.roomschedule.service;


import com.roomschedule.model.RoomScheduleVO;
import com.roomtype.model.RoomTypeVO;

public interface RoomScheduleService {

    RoomScheduleVO findByRoomTypeId(Integer roomTypeId);
}
