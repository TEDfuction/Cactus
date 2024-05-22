package com.roomorderlist.model;

import com.room.model.Room;
import com.roomOrder.model.RoomOrder;
import com.roomType.model.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "room_order_list")
public class RoomOrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_order_list_id", nullable = false)
    private Integer roomOrderListId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotNull
    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_order_id", nullable = false)
    private RoomOrder roomOrder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    public Integer getRoomOrderListId() {
        return roomOrderListId;
    }

    public void setRoomOrderListId(Integer roomOrderListId) {
        this.roomOrderListId = roomOrderListId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomOrder getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(RoomOrder roomOrder) {
        this.roomOrder = roomOrder;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

}