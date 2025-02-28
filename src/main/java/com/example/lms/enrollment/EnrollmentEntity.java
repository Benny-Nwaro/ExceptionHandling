package com.example.lms.enrollment;

import com.example.lms.courses.CourseEntity;
import com.example.lms.users.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(nullable = false, updatable = false)
    private LocalDateTime enrollmentDate;

    public EnrollmentEntity() {
        this.enrollmentDate = LocalDateTime.now();
    }

    public EnrollmentEntity(Long enrollmentId, UserEntity student, CourseEntity course) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public UserEntity getStudent() { return student; }
    public void setStudent(UserEntity student) { this.student = student; }

    public CourseEntity getCourse() { return course; }
    public void setCourse(CourseEntity course) { this.course = course; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", student=" + (student != null ? student.getId() : "null") +
                ", course=" + (course != null ? course.getCourseId() : "null") +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
