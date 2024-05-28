package com.roomtypepic.model;

import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;

@Entity
@Table(name = "room_type_pic")
public class RoomTypePicVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_pic_id", nullable = false)
    private Integer roomPicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @Column(name = "room_pic")
    private byte[] roomPic;


    public Integer getRoomPicId() {
        return roomPicId;
    }

    public void setRoomPicId(Integer roomPicId) {
        this.roomPicId = roomPicId;
    }

    public RoomTypeVO getRoomTypeVO() {
        return roomTypeVO;
    }

    public void setRoomTypeVO(RoomTypeVO roomTypeVO) {
        this.roomTypeVO = roomTypeVO;
    }

    public byte[] getRoomPic() {
        return roomPic;
    }

    public void setRoomPic(byte[] roomPic) {
        this.roomPic = roomPic;
    }


    public RoomTypeVO getRoomType() {
        return roomTypeVO;
    }

    public void setRoomType(RoomTypeVO roomTypeVO) {
        this.roomTypeVO = roomTypeVO;
    }

}