package com.example.lms.courses;

import java.math.BigDecimal;
import java.util.UUID;

public class CourseDTO {
    private UUID courseId;
    private String title;
    private String description;
    private UUID instructorId;
    private BigDecimal coursePrice;
    private String courseImage;

    // Constructor
    public CourseDTO(UUID courseId, String title, String description, UUID instructorId, BigDecimal coursePrice, String courseImage) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.coursePrice = coursePrice;
        this.courseImage = courseImage;
    }


    // Getters & Setters
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getInstructorId() { return instructorId; }
    public void setInstructorId(UUID instructorId) { this.instructorId = instructorId; }

    public BigDecimal getCoursePrice() { return coursePrice; }
    public void setCoursePrice(BigDecimal coursePrice) { this.coursePrice = coursePrice; }

    public String getCourseImage() { return courseImage; }
    public void setCourseImage(String courseImage) { this.courseImage = courseImage; }
}

