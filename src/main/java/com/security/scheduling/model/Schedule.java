
package com.security.scheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {
    private Integer scheduleId;
    private String scheduleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    private String shiftPeriod;
    private Integer createdBy;
    private Boolean isPublished;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public Schedule() {}

    public Schedule(Integer scheduleId, String scheduleName, LocalDate scheduleDate,
                    String shiftPeriod, Integer createdBy, Boolean isPublished,
                    LocalDateTime publishedAt, LocalDateTime createdAt) {
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.scheduleDate = scheduleDate;
        this.shiftPeriod = shiftPeriod;
        this.createdBy = createdBy;
        this.isPublished = isPublished;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getScheduleId() { return scheduleId; }
    public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }

    public String getScheduleName() { return scheduleName; }
    public void setScheduleName(String scheduleName) { this.scheduleName = scheduleName; }

    public LocalDate getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }

    public String getShiftPeriod() { return shiftPeriod; }
    public void setShiftPeriod(String shiftPeriod) { this.shiftPeriod = shiftPeriod; }

    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }

    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}