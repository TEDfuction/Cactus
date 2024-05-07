package com.room.model;


import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_schedule", schema = "cactus")
public class RoomScheduleVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_schedule_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @Convert(disableConversion = true)
    @Column(name = "room_schedule_date", nullable = false)
    private LocalDate roomScheduleDate;

    @Column(name = "room_rsv_amount", nullable = false)
    private Integer roomRsvAmount;

    @Column(name = "room_quantity", nullable = false)
    private Integer roomQuantity;

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

    public LocalDate getRoomScheduleDate() {
        return roomScheduleDate;
    }

    public void setRoomScheduleDate(LocalDate roomScheduleDate) {
        this.roomScheduleDate = roomScheduleDate;
    }

    public Integer getRoomRsvAmount() {
        return roomRsvAmount;
    }

    public void setRoomRsvAmount(Integer roomRsvAmount) {
        this.roomRsvAmount = roomRsvAmount;
    }

    public Integer getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(Integer roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

}