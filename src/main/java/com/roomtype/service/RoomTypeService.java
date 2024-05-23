package com.roomtype.service;


import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.model.RoomTypeVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface RoomTypeService {



    void addRoomType(RoomTypeVORequest roomTypeVORequest);

    void updateRoomType(RoomTypeUpdate roomtypeUpdate);

//    void updateRTS(Integer roomTypeId, Boolean roomTypeStatus);

    List<RoomTypeVO> getAllRoomTypes();

    RoomTypeVO getRoomTypeById(Integer roomTypeId);

    RoomTypeVO getOneRoomType(Integer roomTypeId);

    List<RoomTypeVO> getRTStatus();

    List<Object[]> getAvailableRoomTypes(String roomTypeName, Date checkInDate, Date checkOutDate, Integer roomGuestAmount);

    Optional<RoomTypeVO> getRoomTypeIdByName(String roomTypeName);

    List<Integer> getRoomGuestAmounts(Integer getRoomTypeId);
}
