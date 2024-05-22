package com.room.model;

import com.roomType.model.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

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

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
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