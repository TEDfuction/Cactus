package com.roomtype.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomTypeVORequest {


    @NotNull(message =  "房型編號為必填")
    @Id
    @Column(name = "room_type_id", nullable = false)
    private Integer roomTypeId;


    @NotEmpty(message =  "房型名稱為必填")
    @Size(max = 50)
    @Column(name = "room_type_name", nullable = false, length = 50)
    private String roomTypeName;

    @Size(max = 1000)
    @Column(name = "room_type_content", length = 1000)
    private String roomTypeContent;

    @NotNull
    @Column(name = "room_type_status")
    private Boolean roomTypeStatus = false;

    @NotEmpty(message =  "房型價格說明為必填")
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

//    public Integer getRoomTypeStatus() {
//        return roomTypeStatus;
//    }

//    public void setRoomTypeStatus(Integer roomTypeStatus) {
//        this.roomTypeStatus = roomTypeStatus;
//    }

    public String getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(String roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }
}
