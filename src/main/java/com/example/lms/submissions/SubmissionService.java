package com.example.lms.submissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.assignments.AssignmentRepository;
import com.example.lms.exceptions.DuplicateSubmissionException;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public SubmissionDTO submitAssignment(SubmissionDTO submissionDTO) {
        UserEntity student = userRepository.findById(submissionDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: "
                        + submissionDTO.getStudentId()));

        AssignmentEntity assignment = assignmentRepository.findById(submissionDTO.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with ID: "
                        + submissionDTO.getAssignmentId()));

        if (submissionRepository.existsByStudentAndAssignment(student, assignment)) {
            throw new DuplicateSubmissionException("Student ID " + submissionDTO.getStudentId()
                    + " has already submitted Assignment ID " + submissionDTO.getAssignmentId());
        }

        SubmissionEntity submission = SubmissionMapper.toEntity(submissionDTO, student, assignment);
        submission = submissionRepository.save(submission);

        return SubmissionMapper.toDTO(submission);
    }

    public List<SubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(SubmissionMapper::toDTO).collect(Collectors.toList());
    }

    public SubmissionDTO getSubmissionById(Long submissionId) {
        SubmissionEntity submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        return SubmissionMapper.toDTO(submission);    }
}
