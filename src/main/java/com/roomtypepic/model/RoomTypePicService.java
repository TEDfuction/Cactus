package com.roomtypepic.model;

public interface RoomTypePicService {

    void updateRoomTypePic(RoomTypePicVO roomTypePicVO);

    RoomTypePicVO getOneRoomPic(Integer roomTypeId) throws Exception;
}
