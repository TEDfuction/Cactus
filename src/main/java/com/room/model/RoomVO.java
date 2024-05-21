package com.room.model;

import com.roomtype.model.RoomTypeVO;
import javax.persistence.*;

@Entity
@Table(name = "room", schema = "cactus")
public class RoomVO {
    @Id
    @Column(name = "room_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @Column(name = "room_guest_amount", nullable = false)
    private Integer roomGuestAmount;

    @Column(name = "room_guest_name", length = 100)
    private String roomGuestName;

    @Column(name = "room_status", nullable = false)
    private Boolean roomStatus = false;

    @Column(name = "room_sale_status", nullable = false)
    private Byte roomSaleStatus;

    @Column(name = "room_price", nullable = false)
    private Integer roomPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomTypeVO getRoomType() {
        return roomTypeVO;
    }

    public void setRoomType(RoomTypeVO roomTypeVO) {
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

    public Boolean getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Boolean roomStatus) {
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