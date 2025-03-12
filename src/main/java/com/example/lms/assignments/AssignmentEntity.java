package com.example.lms.assignments;

import com.example.lms.courses.CourseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "assignments")
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID assignment_id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "Text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private LocalDate due_date;


    public AssignmentEntity() {
    }

    public AssignmentEntity(UUID assignmentId, String title,
                            String description, CourseEntity course,
                            LocalDate due_date) {
        this.assignment_id = assignmentId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.due_date = due_date;
    }

    public UUID getAssignmentId() {
        return assignment_id;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignment_id = assignmentId;
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

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }
    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignment_id +
                ", Title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", Course='" + course + '\'' +
                ", Due date=" + due_date +
                '}';
    }
}
