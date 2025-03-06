package com.example.lms.enrollment;

import com.example.lms.exceptions.DuplicateEnrollmentException;
import com.example.lms.exceptions.EnrollmentNotFoundException;
import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentMapper::toDTO).collect(Collectors.toList());
    }

    public EnrollmentDTO getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id).map(EnrollmentMapper::toDTO).orElseThrow(() ->
                new EnrollmentNotFoundException("Enrollment with ID " + id + " not found"));
    }

    public EnrollmentDTO enrollStudent(Long studentId, Long courseId) {
        UserEntity student = userRepository.findById(studentId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Student with ID " + studentId + " not found"));
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EnrollmentNotFoundException("Course with ID " + courseId + " not found"));

        Optional<EnrollmentEntity> existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if (existingEnrollment.isPresent()) {
            throw new DuplicateEnrollmentException("Student with ID " + studentId +
                    " is already enrolled in course with ID " + courseId);
        }

        EnrollmentEntity enrollment = new EnrollmentEntity(null, student, course);
        EnrollmentEntity savedEnrollment = enrollmentRepository.save(enrollment);

        return new EnrollmentDTO(savedEnrollment.getEnrollmentId(), studentId, courseId);
    }

    public void deleteEnrollment(Long id) {
        EnrollmentEntity existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment with ID " + id + " not found"));

        enrollmentRepository.delete(existingEnrollment);
    }
}
