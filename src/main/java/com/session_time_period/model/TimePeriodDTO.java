package com.session_time_period.model;

import java.sql.Time;

public class TimePeriodDTO {

    private Integer sessionTimePeriodId;
    private Time timePeriod;

    // Getters and Setters
    public Integer getSessionTimePeriodId() {
        return sessionTimePeriodId;
    }

    public void setSessionTimePeriodId(Integer sessionTimePeriodId) {
        this.sessionTimePeriodId = sessionTimePeriodId;
    }

    public Time getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Time timePeriod) {
        this.timePeriod = timePeriod;
    }
}

