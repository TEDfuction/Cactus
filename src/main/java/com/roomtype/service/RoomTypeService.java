package com.roomtype.service;

import com.roomtype.dto.RoomTypeVORequest;
import com.roomtype.dto.RoomTypeUpdate;
import com.roomtype.model.RoomTypeVO;

import java.util.List;


public interface RoomTypeService {



    void addRoomType(RoomTypeVORequest roomTypeVORequest);

    void updateRoomType(RoomTypeUpdate roomtypeUpdate);

//    void updateRTS(Integer roomTypeId, Boolean roomTypeStatus);

    List<RoomTypeVO> getAllRoomTypes();


     RoomTypeVO getOneRoomType(Integer roomTypeId);


}
