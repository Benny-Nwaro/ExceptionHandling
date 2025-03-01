package com.example.lms.enrollment;

import java.time.LocalDateTime;

public class EnrollmentDTO {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private LocalDateTime enrollmentDate;

    public EnrollmentDTO() {}
    public EnrollmentDTO(Long enrollmentId, Long studentId, Long courseId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public EnrollmentDTO(Long enrollmentId, Long studentId, Long courseId, LocalDateTime enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
