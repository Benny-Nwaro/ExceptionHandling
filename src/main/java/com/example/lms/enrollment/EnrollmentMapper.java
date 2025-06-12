package com.example.lms.enrollment;

public class EnrollmentMapper {

    public static EnrollmentDTO toDTO(EnrollmentEntity enrollment) {
        return new EnrollmentDTO(
                enrollment.getEnrollmentId(),
                enrollment.getStudent().getId(),
                enrollment.getCourse().getCourseId(),
                enrollment.getEnrollmentDate()
        );
    }

    public static EnrollmentEntity toEntity(EnrollmentDTO enrollmentDTO) {
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setEnrollmentId(enrollmentDTO.getEnrollmentId());
        return enrollment;
    }
}
