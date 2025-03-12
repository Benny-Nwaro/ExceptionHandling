package com.example.lms.enrollment;

import java.time.LocalDateTime;
import java.util.UUID;

public class EnrollmentDTO {
    private UUID enrollmentId;
    private UUID studentId;
    private UUID courseId;
    private LocalDateTime enrollmentDate;

    public EnrollmentDTO() {}
    public EnrollmentDTO(UUID enrollmentId, UUID studentId, UUID courseId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public EnrollmentDTO(UUID enrollmentId, UUID studentId, UUID courseId, LocalDateTime enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public UUID getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(UUID enrollmentId) { this.enrollmentId = enrollmentId; }

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
