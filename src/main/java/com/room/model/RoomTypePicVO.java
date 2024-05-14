package com.room.model;

import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;

@Entity
@Table(name = "room_type_pic", schema = "cactus")
public class RoomTypePicVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_pic_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @Column(name = "room_pic")
    private byte[] roomPic;

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

    public byte[] getRoomPic() {
        return roomPic;
    }

    public void setRoomPic(byte[] roomPic) {
        this.roomPic = roomPic;
    }

}