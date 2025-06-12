package com.example.lms.submissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.users.UserEntity;

public class SubmissionMapper {

    public static SubmissionDTO toDTO(SubmissionEntity submission) {
        return new SubmissionDTO(
                submission.getId(),
                submission.getStudent().getId(),
                submission.getAssignment().getAssignmentId(),
                submission.getSubmittedAt()
        );
    }

    public static SubmissionEntity toEntity(SubmissionDTO dto, UserEntity student,
                                            AssignmentEntity assignment) {
        return new SubmissionEntity(student, assignment);
    }
}
