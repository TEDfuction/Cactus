package com.activities_promotion.model;

import com.activities_order.model.ActivityOrderVO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
@Entity
@Table(name = "activity_promotion")
public class PromotionVO implements Serializable {

    private static final long serialVersionUID = -3296309168519059810L;

    @Id
    @Column(name = "promotion_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer promotionId;

    @Column(name = "promotion_title")
    @Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9()_$&)]{2,30}$", message = "且只能輸入中、英文字母、數字和特殊符號()_&$ , 長度必需在2到30之間")
    @NotEmpty(message = "活動促銷標題請勿空白")
    private String promotionTitle;

    @Column(name = "promotion_content")
    @NotEmpty(message = "活動促銷說明請勿空白")
    private String promotionContent;

    @Column(name = "promotion_discount")
    private Double promotionDiscount;

    @Column(name = "promotion_coupon")
    private Integer promotionCoupon;

    @NotNull(message = "促銷開始時間必須填寫")
    @Column(name = "promotion_started")
    private Date promotionStarted;

    @NotNull(message = "促銷結束時間必須填寫")
    @Column(name = "promotion_end")
    private Date promotionEnd;

    @NotNull(message = "促銷狀態必須選填")
    @Column(name = "promotion_state")
    private Boolean promotionState;

    @OneToMany(mappedBy = "promotionVO", cascade = CascadeType.ALL)
    private Set<ActivityOrderVO> activityOrderVO;

    public PromotionVO(){}

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

    public Double getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(Double promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public Integer getPromotionCoupon() {
        return promotionCoupon;
    }

    public void setPromotionCoupon(Integer promotionCoupon) {
        this.promotionCoupon = promotionCoupon;
    }

    public Date getPromotionStarted() {
        return promotionStarted;
    }

    public void setPromotionStarted(Date promotionStarted) {
        this.promotionStarted = promotionStarted;
    }

    public Date getPromotionEnd() {
        return promotionEnd;
    }

    public void setPromotionEnd(Date promotionEnd) {
        this.promotionEnd = promotionEnd;
    }

    public Boolean getPromotionState() {
        return promotionState;
    }

    public void setPromotionState(Boolean promotionState) {
        this.promotionState = promotionState;
    }

    public Set<ActivityOrderVO> getActivityOrderVO() {
        return activityOrderVO;
    }

    public void setActivityOrderVO(Set<ActivityOrderVO> activityOrderVO) {
        this.activityOrderVO = activityOrderVO;
    }

    @Override
    public String toString() {
        return "PromotionVO{" +
                "promotionId=" + promotionId +
                ", promotionTitle='" + promotionTitle + '\'' +
                ", promotionContent='" + promotionContent + '\'' +
                ", promotionDiscount=" + promotionDiscount +
                ", promotionCoupon=" + promotionCoupon +
                ", promotionStarted=" + promotionStarted +
                ", promotionEnd=" + promotionEnd +
                ", promotionState=" + promotionState +
                ", activityOrderVO=" + activityOrderVO +
                '}';
    }

}
