package com.roomschedule.model;

import com.roomtype.model.RoomTypeVO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "room_schedule", schema = "cactus2")
public class RoomScheduleVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_schedule_id", nullable = false)
    private Integer roomScheduleId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomTypeVO roomTypeVO;

    @NotNull
    @Column(name = "room_schedule_date", nullable = false)
    private LocalDate roomScheduleDate;

    @NotNull
    @Column(name = "room_schedule_amount", nullable = false)
    private Integer roomScheduleAmount;

    public Integer getId() {
        return roomScheduleId;
    }

    public void setId(Integer roomScheduleId) {
        this.roomScheduleId = roomScheduleId;
    }

    public RoomTypeVO getRoomType() {
        return roomTypeVO;
    }

    public void setRoomType(RoomTypeVO roomType) {
        this.roomTypeVO = roomType;
    }

    public LocalDate getRoomScheduleDate() {
        return roomScheduleDate;
    }

    public void setRoomScheduleDate(LocalDate roomScheduleDate) {
        this.roomScheduleDate = roomScheduleDate;
    }

    public Integer getRoomScheduleAmount() {
        return roomScheduleAmount;
    }

    public void setRoomScheduleAmount(Integer roomScheduleAmount) {
        this.roomScheduleAmount = roomScheduleAmount;
    }

}