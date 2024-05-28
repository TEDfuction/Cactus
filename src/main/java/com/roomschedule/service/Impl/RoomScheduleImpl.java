package com.roomschedule.service.Impl;


import com.roomschedule.model.RoomScheduleRepository;
import com.roomschedule.model.RoomScheduleVO;
import com.roomschedule.service.RoomScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomScheduleImpl implements RoomScheduleService {

    @Autowired
    private RoomScheduleRepository roomScheduleRepository;

    @Override
    public RoomScheduleVO findByRoomTypeId(Integer roomTypeId) {
        Optional<RoomScheduleVO> roomScheduleVO = Optional.ofNullable(roomScheduleRepository.getByRoomTypeId(roomTypeId));
        return roomScheduleVO.orElse(null);
    }
}
