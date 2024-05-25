package com.roomtype.dto;


import javax.persistence.Column;
import javax.persistence.Id;

public class RoomTypeStatus {

    @Id
    @Column(name = "room_type_id", nullable = false)
    private Integer roomTypeId;

    @Column(name = "room_type_status")
    private Boolean roomTypeStatus = false;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Boolean getRoomTypeStatus() {
        return roomTypeStatus;
    }

    public void setRoomTypeStatus(Boolean roomTypeStatus) {
        this.roomTypeStatus = roomTypeStatus;
    }


}
