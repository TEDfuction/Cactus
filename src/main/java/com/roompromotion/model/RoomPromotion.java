package com.roompromotion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "room_promotion")
public class RoomPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

    @Size(max = 40)
    @NotEmpty(message = "促銷標題:請勿空白")
    @Column(name = "promotion_title", nullable = false, length = 40)
    private String promotionTitle;

    @Size(max = 1000)
    @NotEmpty(message = "促銷敘述:請勿空白")
    @Column(name = "promotion_content", nullable = false, length = 1000)
    private String promotionContent;

    @NotNull
    @Column(name = "promotion_state", nullable = false)
    private Byte promotionState;

    @NotNull(message = "促銷折扣:請勿空白")
    @Column(name = "promotion_discount", nullable = false)
    private Double promotionDiscount;

    @NotNull(message = "請選擇促銷開始時間")
    @Column(name = "promotion_started", nullable = false)
    private LocalDate promotionStarted;

    @NotNull(message = "請選擇促銷結束時間")
    @Column(name = "promotion_end", nullable = false)
    private LocalDate promotionEnd;

    public RoomPromotion() {
        super();
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPromotionContent() {
        return promotionContent;
    }

    public void setPromotionContent(String promotionContent) {
        this.promotionContent = promotionContent;
    }

    public Byte getPromotionState() {
        return promotionState;
    }

    public void setPromotionState(Byte promotionState) {
        this.promotionState = promotionState;
    }

    public Double getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(Double promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public LocalDate getPromotionStarted() {
        return promotionStarted;
    }

    public void setPromotionStarted(LocalDate promotionStarted) {
        this.promotionStarted = promotionStarted;
    }

    public LocalDate getPromotionEnd() {
        return promotionEnd;
    }

    public void setPromotionEnd(LocalDate promotionEnd) {
        this.promotionEnd = promotionEnd;
    }

}