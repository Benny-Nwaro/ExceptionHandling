package com.example.lms.sumissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.assignments.AssignmentRepository;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             UserRepository userRepository,
                             AssignmentRepository assignmentRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public SubmissionEntity submitAssignment(Long studentId, Long assignmentId) {
        // Check if student and assignment exist
        UserEntity student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssignmentEntity assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        // Check if the submission already exists
        if (submissionRepository.existsByStudentAndAssignment(student, assignment)) {
            throw new RuntimeException("Submission already exists for this assignment");
        }

        // Save submission
        SubmissionEntity submission = new SubmissionEntity(student, assignment);
        return submissionRepository.save(submission);
    }

    public List<SubmissionEntity> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    public Optional<SubmissionEntity> getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }
}
