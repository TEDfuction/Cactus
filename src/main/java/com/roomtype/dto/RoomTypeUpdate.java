package com.roomtype.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class RoomTypeUpdate {

    @Id
    @Column(name = "room_type_id", nullable = false)
    private Integer roomTypeId;

    @Size(max = 50)
    @Column(name = "room_type_name", nullable = false, length = 50)
    private String roomTypeName;

    @Size(max = 1000)
    @Column(name = "room_type_content", length = 1000)
    private String roomTypeContent;

    @Size(max = 50)
    @Column(name = "room_type_price", length = 50)
    private String roomTypePrice;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeContent() {
        return roomTypeContent;
    }

    public void setRoomTypeContent(String roomTypeContent) {
        this.roomTypeContent = roomTypeContent;
    }

    public String getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(String roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }
}
