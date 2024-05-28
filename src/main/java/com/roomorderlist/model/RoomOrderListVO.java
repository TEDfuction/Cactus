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


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_Id", nullable = false)
    private RoomVO roomVO;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_order_id", nullable = false)
    private RoomOrderVO roomOrder;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    public Integer getRoomOrderListId() {
        return roomOrderListId;
    }

    public void setRoomOrderListId(Integer roomOrderListId) {
        this.roomOrderListId = roomOrderListId;
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
        this.roomTypeVO = roomType;
    }

    public RoomVO getRoomVO() {
        return roomVO;
    }

    public void setRoomVO(RoomVO roomVO) {
        this.roomVO = roomVO;
    }

    public RoomTypeVO getRoomTypeVO() {
        return roomTypeVO;
    }

    public void setRoomTypeVO(RoomTypeVO roomTypeVO) {
        this.roomTypeVO = roomTypeVO;
    }
}