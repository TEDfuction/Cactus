package com.roomorderlist.model;

import com.room.model.RoomVO;
import com.roomorder.model.RoomOrderVO;
import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "room_order_list")
public class RoomOrderListVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_order_list_id", nullable = false)
    private Integer roomOrderListId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_Id", nullable = false)
    private RoomVO room;

    @NotNull
    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_order_id", nullable = false)
    private RoomOrderVO roomOrder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    public Integer getRoomOrderListId() {
        return roomOrderListId;
    }

    public void setRoomOrderListId(Integer roomOrderListId) {
        this.roomOrderListId = roomOrderListId;
    }

    public RoomVO getRoom() {
        return room;
    }

    public void setRoom(RoomVO room) {
        this.room = room;
    }

    public RoomOrderVO getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(RoomOrderVO roomOrder) {
        this.roomOrder = roomOrder;
    }

    public RoomTypeVO getRoomType() {
        return roomTypeVO;
    }

    public void setRoomType(RoomTypeVO roomType) {
        this.roomTypeVO = roomTypeVO;
    }
}