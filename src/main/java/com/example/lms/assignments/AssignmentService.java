package com.example.lms.assignments;

import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.AssignmentNotFoundException;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.exceptions.DuplicateAssignmentException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;


    public AssignmentService(AssignmentRepository assignmentRepository,  CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;

    };

    public List<AssignmentDTO> getAssignments(){
        return assignmentRepository.findAll().stream()
                .map(AssignmentMapper::toDTO).collect(Collectors.toList());
    };

    public AssignmentDTO getAssignmentById(UUID id){
        AssignmentEntity assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment with id " + id + " not found"));
        return AssignmentMapper.toDTO(assignment);
    }

    public AssignmentDTO saveAssignment (AssignmentDTO assignmentDTO){
        CourseEntity course = courseRepository.findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        AssignmentEntity assignment = AssignmentMapper.toEntity(assignmentDTO, course);
        if (assignmentRepository.existsByTitle(assignment.getTitle())) {
            throw new DuplicateAssignmentException("Assignment with title '" + assignment.getTitle() + "' already exists");
        }
        assignment = assignmentRepository.save(assignment);

        return AssignmentMapper.toDTO(assignment);
    }

    public void deleteById(UUID id) {
        if (!assignmentRepository.existsById(id)) {
            throw new AssignmentNotFoundException("Assignment with ID " + id + " not found");
        }
        assignmentRepository.deleteById(id);
    }
}
