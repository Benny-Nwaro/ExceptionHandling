package com.example.lms.submissions;

import java.time.LocalDateTime;

public class SubmissionDTO {
    private Long submissionId;
    private Long studentId;
    private Long assignmentId;
    private LocalDateTime submittedAt;

    public SubmissionDTO() {}

    public SubmissionDTO(Long submissionId, Long studentId, Long assignmentId, LocalDateTime submittedAt) {
        this.submissionId = submissionId;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.submittedAt = submittedAt;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
