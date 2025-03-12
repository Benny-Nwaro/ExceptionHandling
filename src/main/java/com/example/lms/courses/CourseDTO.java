package com.example.lms.courses;

import java.util.UUID;

public class CourseDTO {
    private UUID courseId;
    private String title;
    private String description;
    private UUID instructorId;

    // Constructor
    public CourseDTO(UUID courseId, String title, String description, UUID instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
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
}

