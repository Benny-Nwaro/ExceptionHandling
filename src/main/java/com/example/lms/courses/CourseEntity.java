package com.example.lms.courses;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID courseId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private UUID instructorId;

    @Column(nullable = true)
    private String courseImage;

    @Column(nullable = false)
    private BigDecimal coursePrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public CourseEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public CourseEntity(UUID courseId, String title, String description, UUID instructorId,  BigDecimal coursePrice, String courseImage) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.courseImage = courseImage;
        this.coursePrice = coursePrice;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getInstructorId() { return instructorId; }
    public void setInstructorId(UUID instructorId) { this.instructorId = instructorId; }

    public String getCourseImage() { return courseImage; }
    public void setCourseImage(String courseImage) { this.courseImage = courseImage; }

    public BigDecimal getCoursePrice() { return coursePrice; }
    public void setCoursePrice(BigDecimal coursePrice) { this.coursePrice = coursePrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructorId=" + instructorId +
                ", coursePrice=" + coursePrice +
                ", createdAt=" + createdAt +
                '}';
    }
}
