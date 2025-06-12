package com.example.lms.assignments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class AssignmentDTO {
    private UUID assignmentId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Course ID is required")
    private UUID courseId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    public AssignmentDTO() {}

    public AssignmentDTO(UUID assignmentId, String title, String description, UUID courseId, LocalDate dueDate) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.dueDate = dueDate;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
