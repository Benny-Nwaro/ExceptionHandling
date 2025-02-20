package com.example.lms.services;


import com.example.lms.entities.AssignmentEntity;
import com.example.lms.repositories.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<AssignmentEntity> getAssignmentsByCourse(Long courseId) {
        return assignmentRepository.findByCourseCourseId(courseId);
    }

    public AssignmentEntity createAssignment(AssignmentEntity assignment) {
        return assignmentRepository.save(assignment);
    }

    public Optional<AssignmentEntity> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}

