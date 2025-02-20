package com.example.lms.services;


import com.example.lms.entities.SubmissionEntity;
import com.example.lms.repositories.SubmissionsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    private final SubmissionsRepository submissionRepository;

    public SubmissionService(SubmissionsRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<SubmissionEntity> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentUserId(studentId);
    }

    public List<SubmissionEntity> getSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.findByAssignmentAssignmentId(assignmentId);
    }

    public SubmissionEntity submitAssignment(SubmissionEntity submission) {
        return submissionRepository.save(submission);
    }

    public Optional<SubmissionEntity> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }
}
