package com.example.lms.assignments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    };

    public List<AssignmentEntity> getAssignments(){
        return assignmentRepository.findAll();
    };

    public AssignmentEntity getAssignmentById(Long id){
        return assignmentRepository.findById(id).orElse(null);
    }

    public AssignmentEntity saveAssignment (AssignmentEntity assignment){
        return assignmentRepository.save(assignment);
    }
    public void deleteById(Long id){
         assignmentRepository.deleteById(id);
    }

}
