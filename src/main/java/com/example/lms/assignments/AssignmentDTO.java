package com.example.lms.assignments;

import java.util.Date;

public class AssignmentDTO {
    private Long assignmentId;
    private String title;
    private String description;
    private Long courseId;
    private Date dueDate;

    public AssignmentDTO() {}

    public AssignmentDTO(Long assignmentId, String title, String description, Long courseId, Date dueDate) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.dueDate = dueDate;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
