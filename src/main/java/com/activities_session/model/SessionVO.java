package com.activities_session.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import com.activities_item.model.ItemVO;
import com.activities_order.model.ActivityOrderVO;
import com.session_time_period.model.Time_PeriodVO;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "activity_session")
public class SessionVO implements Serializable {
    private static final long serialVersionUID = -243893504408926223L;

    @Id
    @Column(name = "activity_session_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activitySessionId;

    @ManyToOne
    @JoinColumn(name = "activity_id",referencedColumnName = "activity_id")
    @NotNull(message = "活動項目必須選填")
    private ItemVO itemVO;

    @Column(name = "activity_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "場次日期請勿空白")
    private Date activityDate;


    @Column(name = "duration")
    @NotNull(message = "活動時長請勿空白，且輸入的內容必須為數字")
    @Min(message = "活動時長必須為數字，且不得為0", value = 0)
    @Digits(integer = 2, fraction = 1, message = "活動時長小數點上限為1位")
    private Double duration;
    
    @Column(name = "activity_location")
    @NotEmpty(message="活動地點請勿空白")
    private String activityLocation;

    @Column(name = "activity_max_part")
    @NotNull(message = "活動人數上限請勿空白，且只能填寫整數")
    @PositiveOrZero(message = "活動人數上限請勿空白，且只能填寫整數")
    private Integer activityMaxPart;

    @Column(name = "activity_min_part")
    @NotNull(message = "活動人數下限請勿空白，且只能填寫整數")
    @PositiveOrZero(message = "活動人數下限請勿空白，且只能填寫整數")
    private Integer activityMinPart;

    @Column(name = "enroll_total")
    @NotNull(message = "報名總數請勿空白，且只能填寫整數")
    @PositiveOrZero(message = "報名總數請勿空白，且只能填寫整數")
    private Integer enrollTotal;

    @Column(name = "enroll_started")
//    @Column(name = "enroll_started", columnDefinition="datetime")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "開始報名時間請勿空白")
    private Timestamp enrollStarted;

    @Column(name = "enroll_end")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @Column(name = "enroll_end", columnDefinition="datetime")
    @NotNull(message = "結束報名時間請勿空白")
    private Timestamp enrollEnd;

    @Column(name = "activity_session_state")
    @NotNull(message = "場次狀態必須選擇")
    private Integer activitySessionState;

    @Column(name = "activity_note")
    private String activityNote;

    @OneToMany(mappedBy = "sessionVO", cascade = CascadeType.ALL)
    private Set<ActivityOrderVO> activityOrderVO;

    @OneToMany(mappedBy = "sessionVO", cascade = CascadeType.ALL)
    private Set<Time_PeriodVO> time_periodVO;

    public SessionVO(){
    }


    public Integer getActivitySessionId() {
        return activitySessionId;
    }

    public void setActivitySessionId(Integer activitySessionId) {
        this.activitySessionId = activitySessionId;
    }


    public String getActivityLocation() {
		return activityLocation;
	}


	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}


	public Integer getActivityMaxPart() {
        return activityMaxPart;
    }

    public void setActivityMaxPart(Integer activityMaxPart) {
        this.activityMaxPart = activityMaxPart;
    }


    public Integer getActivityMinPart() {
        return activityMinPart;
    }

    public void setActivityMinPart(Integer activityMinPart) {
        this.activityMinPart = activityMinPart;
    }


    public Integer getEnrollTotal() {
        return enrollTotal;
    }

    public void setEnrollTotal(Integer enrollTotal) {
        this.enrollTotal = enrollTotal;
    }


    public Timestamp getEnrollStarted() {
        return enrollStarted;
    }

    public void setEnrollStarted(Timestamp enrollStarted) {
        this.enrollStarted = enrollStarted;
    }


    public Timestamp getEnrollEnd() {
        return enrollEnd;
    }

    public void setEnrollEnd(Timestamp enrollEnd) {
        this.enrollEnd = enrollEnd;
    }

    

    public ItemVO getItemVO() {
		return itemVO;
	}


	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}


	public Integer getActivitySessionState() {
        return activitySessionState;
    }

    public void setActivitySessionState(Integer activitySessionState) {
        this.activitySessionState = activitySessionState;
    }

    public String getActivityNote() {
        return activityNote;
    }

    public void setActivityNote(String activityNote) {
        this.activityNote = activityNote;
    }


	public Date getActivityDate() {
		return activityDate;
	}


	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}


	public Double getDuration() {
		return duration;
	}


	public void setDuration(Double duration) {
		this.duration = duration;
	}

    public Set<ActivityOrderVO> getActivityOrderVO() {
        return activityOrderVO;
    }

    public void setActivityOrderVO(Set<ActivityOrderVO> activityOrderVO) {
        this.activityOrderVO = activityOrderVO;
    }

    public Set<Time_PeriodVO> getTime_periodVO() {
        return time_periodVO;
    }

    public void setTime_periodVO(Set<Time_PeriodVO> time_periodVO) {
        this.time_periodVO = time_periodVO;
    }
}


