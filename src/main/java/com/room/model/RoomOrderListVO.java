package com.room.model;


import com.roomorder.model.RoomOrderVO;
import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;
@Entity
@Table(name = "room_order_list", schema = "cactus")
public class RoomOrderListVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_order_list_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private com.room.model.RoomVO roomVO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_order_id", nullable = false)
    private RoomOrderVO roomOrderVO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.room.model.RoomVO getRoom() {
        return roomVO;
    }

    public void setRoom(com.room.model.RoomVO roomVO) {
        this.roomVO = roomVO;
    }

    public RoomOrderVO getRoomOrder() {
        return roomOrderVO;
    }

    public void setRoomOrder(RoomOrderVO roomOrderVO) {
        this.roomOrderVO = roomOrderVO;
    }

    public RoomTypeVO getRoomType() {
        return roomTypeVO;
    }

    public void setRoomType(RoomTypeVO roomTypeVO) {
        this.roomTypeVO = roomTypeVO;
    }

}