package com.activities_attendees.model;

import com.activities_order.model.ActivityOrderVO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "activity_attendees")
public class AttendeesVO implements Serializable {

    private static final long serialVersionUID = 6765373312995959283L;

    @Id
    @Column(name = "activity_attendees_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityAttendeesId;

    @ManyToOne
    @JoinColumn(name = "activity_order_id", referencedColumnName = "activity_order_id")
    private ActivityOrderVO activityOrderVO;

    @Column(name = "attendees_name", nullable = false)
    @NotEmpty(message = "參加人員姓名：請勿空白")
    @Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$", message = "參加人員名稱: 只能是中、英文字母、數字,且長度必需在2到10之間")
    private String attendeesName;

    @Column(name = "member_gender", nullable = false)
    private Integer memberGender;

    @Column(name = "attendees_idnumber", nullable = false)
    @NotEmpty(message = "身分證字號：請勿空白")
    @Pattern(regexp = "^[A-Z][1-2][0-9]{8}$" , message = "長度為10，且開頭為大寫英文字母")
    private String attendeesIdNumber;

    @Column(name = "attendees_phone",
            columnDefinition = "char",
            nullable = false)
    @NotEmpty(message="手機號碼: 請勿空白")
    @Pattern(regexp="09\\d{8}", message = "請輸入10位數字，且開頭必須為09")
    private String attendeesPhone;

    @Column(name = "attendees_email", nullable = false )
    @Email
    @NotEmpty(message = "參加人員信箱：請勿空白")
    private String attendeesEmail;

    public AttendeesVO(){

    }

    public Integer getActivityAttendeesId() {
        return activityAttendeesId;
    }

    public void setActivityAttendeesId(Integer activityAttendeesId) {
        this.activityAttendeesId = activityAttendeesId;
    }

    public ActivityOrderVO getActivityOrderVO() {
        return activityOrderVO;
    }

    public void setActivityOrderVO(ActivityOrderVO activityOrderVO) {
        this.activityOrderVO = activityOrderVO;
    }

    public String getAttendeesName() {
        return attendeesName;
    }

    public void setAttendeesName(String attendeesName) {
        this.attendeesName = attendeesName;
    }

    public Integer getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(Integer memberGender) {
        this.memberGender = memberGender;
    }

    public String getAttendeesIdNumber() {
        return attendeesIdNumber;
    }

    public void setAttendeesIdNumber(String attendeesIdNumber) {
        this.attendeesIdNumber = attendeesIdNumber;
    }

    public String getAttendeesPhone() {
        return attendeesPhone;
    }

    public void setAttendeesPhone(String attendeesPhone) {
        this.attendeesPhone = attendeesPhone;
    }

    public String getAttendeesEmail() {
        return attendeesEmail;
    }

    public void setAttendeesEmail(String attendeesEmail) {
        this.attendeesEmail = attendeesEmail;
    }

    @Override
    public String toString() {
        return "AttendeesVO{" +
                "activityAttendeesId=" + activityAttendeesId +
                ", activityOrderVO=" + activityOrderVO +
                ", attendeesName='" + attendeesName + '\'' +
                ", memberGender=" + memberGender +
                ", attendeesIdNumber='" + attendeesIdNumber + '\'' +
                ", attendeesPhone='" + attendeesPhone + '\'' +
                ", attendeesEmail='" + attendeesEmail + '\'' +
                '}';
    }

}

