package com.roomorder.model;

import com.member.model.MemberVO;
import com.roomorderlist.model.RoomOrderListVO;
import com.roompromotion.model.RoomPromotionVO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_order", schema = "cactus")
public class RoomOrderVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_order_id", nullable = false)
    private Integer roomOrderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberVO memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private RoomPromotionVO promotionId;

    @Convert(disableConversion = true)
    @Column(name = "room_order_date", nullable = false)
    private LocalDate roomOrderDate;

    @Column(name = "room_order_status", nullable = false)
    private Boolean roomOrderStatus = false;

    @Column(name = "room_amount", nullable = false)
    private Integer roomAmount;

    @Column(name = "pay_amount", nullable = false)
    private Integer payAmount;

    @Column(name = "promotion_price")
    private Integer promotionPrice;

    @Convert(disableConversion = true)
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Convert(disableConversion = true)
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "id_confirm")
    private byte[] idConfirm;

    @Column(name = "room_order_id_req", length = 1000)
    private String roomOrderIdReq;

    @OneToOne (mappedBy = "roomOrder")
    private RoomOrderListVO roomOrderList;

    public RoomOrderListVO getRoomOrderList() {
        return roomOrderList;
    }

    public void setRoomOrderList(RoomOrderListVO roomOrderList) {
        this.roomOrderList = roomOrderList;
    }

    public Integer getRoomOrderId() {
        return roomOrderId;
    }

    public void setRoomOrderId(Integer roomOrderId) {
        this.roomOrderId = roomOrderId;
    }

    public MemberVO getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberVO memberId) {
        this.memberId = memberId;
    }

    public RoomPromotionVO getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(RoomPromotionVO promotionId) {
        this.promotionId = promotionId;
    }

    public LocalDate getRoomOrderDate() {
        return roomOrderDate;
    }

    public void setRoomOrderDate(LocalDate roomOrderDate) {
        this.roomOrderDate = roomOrderDate;
    }

    public Boolean getRoomOrderStatus() {
        return roomOrderStatus;
    }

    public void setRoomOrderStatus(Boolean roomOrderStatus) {
        this.roomOrderStatus = roomOrderStatus;
    }

    public Integer getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(Integer roomAmount) {
        this.roomAmount = roomAmount;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Integer promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public byte[] getIdConfirm() {
        return idConfirm;
    }

    public void setIdConfirm(byte[] idConfirm) {
        this.idConfirm = idConfirm;
    }

    public String getRoomOrderIdReq() {
        return roomOrderIdReq;
    }

    public void setRoomOrderIdReq(String roomOrderIdReq) {
        this.roomOrderIdReq = roomOrderIdReq;
    }

}