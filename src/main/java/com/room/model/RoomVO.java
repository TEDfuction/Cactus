package com.room.model;


import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "room")
public class RoomVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @NotNull(message = "住客人數:請勿空白")
    @Column(name = "room_guest_amount", nullable = false)
    private Integer roomGuestAmount;

    @Size(max = 100)
    @Column(name = "room_guest_name", length = 100)
    private String roomGuestName;

    @NotNull
    @Column(name = "room_status", nullable = false)
    private Byte roomStatus;

    @NotNull
    @Column(name = "room_sale_status", nullable = false)
    private Byte roomSaleStatus;

    @NotNull(message = "房間價格:請勿空白")
    @Column(name = "room_price", nullable = false)
    private Integer roomPrice;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public RoomTypeVO getRoomTypeVO() {
        return roomTypeVO;
    }

    public void setRoomTypeVO(RoomTypeVO roomTypeVO) {
        this.roomTypeVO = roomTypeVO;
    }

    public Integer getRoomGuestAmount() {
        return roomGuestAmount;
    }

    public void setRoomGuestAmount(Integer roomGuestAmount) {
        this.roomGuestAmount = roomGuestAmount;
    }

    public String getRoomGuestName() {
        return roomGuestName;
    }

    public void setRoomGuestName(String roomGuestName) {
        this.roomGuestName = roomGuestName;
    }

    public Byte getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Byte roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Byte getRoomSaleStatus() {
        return roomSaleStatus;
    }

    public void setRoomSaleStatus(Byte roomSaleStatus) {
        this.roomSaleStatus = roomSaleStatus;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }

}