
package com.security.scheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Post {
    private Integer postId;
    private String postName;
    private String postLocation;
    private String postType;
    private String requiredLicenseType;
    private Integer minimumExperienceMonths;
    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public Post() {}

    public Post(Integer postId, String postName, String postLocation, String postType,
                String requiredLicenseType, Integer minimumExperienceMonths,
                Boolean isActive, LocalDateTime createdAt) {
        this.postId = postId;
        this.postName = postName;
        this.postLocation = postLocation;
        this.postType = postType;
        this.requiredLicenseType = requiredLicenseType;
        this.minimumExperienceMonths = minimumExperienceMonths;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }


    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }

    public String getPostName() { return postName; }
    public void setPostName(String postName) { this.postName = postName; }

    public String getPostLocation() { return postLocation; }
    public void setPostLocation(String postLocation) { this.postLocation = postLocation; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String getRequiredLicenseType() { return requiredLicenseType; }
    public void setRequiredLicenseType(String requiredLicenseType) { this.requiredLicenseType = requiredLicenseType; }

    public Integer getMinimumExperienceMonths() { return minimumExperienceMonths; }
    public void setMinimumExperienceMonths(Integer minimumExperienceMonths) { this.minimumExperienceMonths = minimumExperienceMonths; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}