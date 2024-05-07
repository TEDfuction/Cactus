package com.room.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_promotion", schema = "cactus")
public class RoomPromotionVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

    @Column(name = "promotion_title", nullable = false, length = 40)
    private String promotionTitle;

    @Column(name = "promotion_content", nullable = false, length = 1000)
    private String promotionContent;

    @Column(name = "promotion_state", nullable = false)
    private Byte promotionState;

    @Column(name = "promotion_discount", nullable = false)
    private Double promotionDiscount;

    @Convert(disableConversion = true)
    @Column(name = "promotion_started", nullable = false)
    private LocalDate promotionStarted;

    @Convert(disableConversion = true)
    @Column(name = "promotion_end", nullable = false)
    private LocalDate promotionEnd;

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