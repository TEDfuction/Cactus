package com.activities_order.model;

import com.activities_attendees.model.AttendeesVO;
import com.activities_promotion.model.PromotionVO;
import com.activities_session.model.SessionVO;
import com.member.model.MemberVO;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "activity_order")
public class ActivityOrderVO implements Serializable {

    private static final long serialVersionUID = -2084656980228182148L;


    @Id
    @Column(name = "activity_order_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityOrderId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberVO memberVO;

    @ManyToOne
    @JoinColumn(name = "activity_session_id", referencedColumnName = "activity_session_id")
    private SessionVO sessionVO;

    @ManyToOne
    @JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")
    private PromotionVO promotionVO;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "enroll_number")
    private Integer enrollNumber;

    @Column(name = "order_amount")
    private Integer orderAmount;

    @Column(name = "promotion_price")
    private Integer promotionPrice;

    @Column(name = "pay_amount")
    private  Integer payAmount;

    @Column(name = "order_state")
    private Integer orderState;

    @Column(name = "refund_state")
    private Integer refundState;

    @Column(name = "order_memo")
    private String orderMemo;

    @OneToMany(mappedBy = "activityOrderVO", cascade = CascadeType.ALL)
    private Set<AttendeesVO> attendeesVO;


    public ActivityOrderVO() {
    }

    public Integer getActivityOrderId() {
        return activityOrderId;
    }

    public void setActivityOrderId(Integer activityOrderId) {
        this.activityOrderId = activityOrderId;
    }

    public MemberVO getMemberVO() {
        return memberVO;
    }

    public void setMemberVO(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    public SessionVO getSessionVO() {
        return sessionVO;
    }

    public void setSessionVO(SessionVO sessionVO) {
        this.sessionVO = sessionVO;
    }

    public PromotionVO getPromotionVO() {
        return promotionVO;
    }

    public void setPromotionVO(PromotionVO promotionVO) {
        this.promotionVO = promotionVO;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Integer promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public Set<AttendeesVO> getAttendeesVO() {
        return attendeesVO;
    }

    public void setAttendeesVO(Set<AttendeesVO> attendeesVO) {
        this.attendeesVO = attendeesVO;
    }

    @Override
    public String toString() {
        return "ActivityOrderVO{" +
                "activityOrderId=" + activityOrderId +
                ", memberVO=" + memberVO +
                ", sessionVO=" + sessionVO +
                ", promotionVO=" + promotionVO +
                ", orderTime=" + orderTime +
                ", enrollNumber=" + enrollNumber +
                ", orderAmount=" + orderAmount +
                ", promotionPrice=" + promotionPrice +
                ", payAmount=" + payAmount +
                ", orderState=" + orderState +
                ", refundState=" + refundState +
                ", orderMemo='" + orderMemo + '\'' +
                ", attendeesVO=" + attendeesVO +
                '}';
    }
}