
package com.security.scheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Shift {
    private Integer shiftId;
    private String shiftName;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private BigDecimal durationHours;
    private Boolean isOvernight;
    private BigDecimal shiftPremium;


    public Shift() {}

    public Shift(Integer shiftId, String shiftName, LocalTime startTime, LocalTime endTime,
                 BigDecimal durationHours, Boolean isOvernight, BigDecimal shiftPremium) {
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.isOvernight = isOvernight;
        this.shiftPremium = shiftPremium;
    }


    public Integer getShiftId() { return shiftId; }
    public void setShiftId(Integer shiftId) { this.shiftId = shiftId; }

    public String getShiftName() { return shiftName; }
    public void setShiftName(String shiftName) { this.shiftName = shiftName; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public BigDecimal getDurationHours() { return durationHours; }
    public void setDurationHours(BigDecimal durationHours) { this.durationHours = durationHours; }

    public Boolean getIsOvernight() { return isOvernight; }
    public void setIsOvernight(Boolean isOvernight) { this.isOvernight = isOvernight; }

    public BigDecimal getShiftPremium() { return shiftPremium; }
    public void setShiftPremium(BigDecimal shiftPremium) { this.shiftPremium = shiftPremium; }
}