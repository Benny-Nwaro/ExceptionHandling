package com.example.lms.assignments;

import com.example.lms.courses.CourseEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "assignments")
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long assignment_id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "Text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date due_date;


    public AssignmentEntity() {
    }

    public AssignmentEntity(long assignmentId, String title,
                            String description, CourseEntity course,
                            Date due_date) {
        this.assignment_id = assignmentId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.due_date = due_date;
    }

    public long getAssignmentId() {
        return assignment_id;
    }

    public void setAssignmentId(long assignmentId) {
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

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
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
