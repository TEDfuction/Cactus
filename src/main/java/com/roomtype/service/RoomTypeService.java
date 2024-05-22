package com.roomtype.service;

import com.controller.roomtype.dto.RoomTypeUpdate;
import com.controller.roomtype.dto.RoomTypeVORequest;
import com.controller.roomtype.model.RoomTypeVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface RoomTypeService {



    void addRoomType(RoomTypeVORequest roomTypeVORequest);
    void updateRoomType(RoomTypeUpdate roomtypeUpdate);

//    void updateRTS(Integer roomTypeId, Boolean roomTypeStatus);

    List<RoomTypeVO> getAllRoomTypes();


     RoomTypeVO getOneRoomType(Integer roomTypeId);

    List<RoomTypeVO> getRTStatus();

    List<Object[]> getAvailableRoomTypes(String roomTypeName, Date checkInDate, Date checkOutDate, Integer roomGuestAmount);

    Optional<RoomTypeVO> getRoomTypeIdByName(String roomTypeName);

    List<Integer> getRoomGuestAmounts(Integer getRoomTypeId);
}
