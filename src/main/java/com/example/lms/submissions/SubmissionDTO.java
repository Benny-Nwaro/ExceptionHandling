package com.example.lms.submissions;

import java.time.LocalDateTime;
import java.util.UUID;

public class SubmissionDTO {
    private UUID submissionId;
    private UUID studentId;
    private UUID assignmentId;
    private LocalDateTime submittedAt;

    public SubmissionDTO() {}

    public SubmissionDTO(UUID submissionId, UUID studentId, UUID assignmentId, LocalDateTime submittedAt) {
        this.submissionId = submissionId;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.submittedAt = submittedAt;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
