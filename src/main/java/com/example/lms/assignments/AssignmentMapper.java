package com.example.lms.assignments;

import com.example.lms.courses.CourseEntity;

public class AssignmentMapper {

    public static AssignmentDTO toDTO(AssignmentEntity assignment) {
        return new AssignmentDTO(
                assignment.getAssignmentId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getCourse().getCourseId(),
                assignment.getDue_date()
        );
    }

    public static AssignmentEntity toEntity(AssignmentDTO dto, CourseEntity course) {
        return new AssignmentEntity(
                dto.getAssignmentId() != null ? dto.getAssignmentId() : null,
                dto.getTitle(),
                dto.getDescription(),
                course,
                dto.getDueDate()
        );
    }
}
