package com.example.lms.submissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.users.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "submissions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "assignment_id"})})
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "submission_id")
    private UUID submission_id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();


    public SubmissionEntity() {}

    public SubmissionEntity(UserEntity student, AssignmentEntity assignment) {
        this.student = student;
        this.assignment = assignment;
        this.submittedAt = LocalDateTime.now();
    }

    public UUID getId() { return submission_id; }

    public UserEntity getStudent() { return student; }
    public void setStudent(UserEntity student) { this.student = student; }

    public AssignmentEntity getAssignment() { return assignment; }
    public void setAssignment(AssignmentEntity assignment) { this.assignment = assignment; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public UUID getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(UUID submission_id) {
        this.submission_id = submission_id;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submission_id +
                ", Student='" + student + '\'' +
                ", Assignment='" + assignment.getTitle() + '\'' +
                ", submitted at=" + submittedAt +
                '}';
    }
}
