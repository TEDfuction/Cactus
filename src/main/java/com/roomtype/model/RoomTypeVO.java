package com.roomtype.model;


import com.room.model.RoomVO;
import com.roomorderlist.model.RoomOrderListVO;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "room_type", schema = "cactus")
public class RoomTypeVO {

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

    @OneToMany(mappedBy = "roomTypeVO")
    private Set<RoomVO> rooms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "roomTypeVO")
    private Set<RoomOrderListVO> roomOrderListVOS = new LinkedHashSet<>();

    @OneToMany(mappedBy = "roomTypeVO")
    private Set<RoomScheduleVO> roomScheduleVOS = new LinkedHashSet<>();

    @OneToMany(mappedBy = "roomTypeVO")
    private Set<RoomTypePicVO> roomTypePicVOS = new LinkedHashSet<>();

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

    public Boolean getRoomTypeStatus() {
        return roomTypeStatus;
    }

    public void setRoomTypeStatus(Boolean roomTypeStatus) {
        this.roomTypeStatus = roomTypeStatus;
    }

    public String getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(String roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }

    public Set<RoomVO> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomVO> rooms) {
        this.rooms = rooms;
    }

    public Set<RoomOrderListVO> getRoomOrderListVOS() {
        return roomOrderListVOS;
    }

    public void setRoomOrderListVOS(Set<RoomOrderListVO> roomOrderListVOS) {
        this.roomOrderListVOS = roomOrderListVOS;
    }

    public Set<RoomScheduleVO> getRoomScheduleVOS() {
        return roomScheduleVOS;
    }

    public void setRoomScheduleVOS(Set<RoomScheduleVO> roomScheduleVOS) {
        this.roomScheduleVOS = roomScheduleVOS;
    }

    public Set<RoomTypePicVO> getRoomTypePicVOS() {
        return roomTypePicVOS;
    }

    public void setRoomTypePicVOS(Set<RoomTypePicVO> roomTypePicVOS) {
        this.roomTypePicVOS = roomTypePicVOS;
    }
}