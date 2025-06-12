package com.example.lms.enrollment;

import com.example.lms.courses.CourseDTO;
import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseMapper;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.exceptions.DuplicateEnrollmentException;
import com.example.lms.exceptions.EnrollmentNotFoundException;
import com.example.lms.users.UserDTO;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserMapper;
import com.example.lms.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public EnrollmentDTO getEnrollmentById(UUID id) {
        return enrollmentRepository.findById(id).map(EnrollmentMapper::toDTO).orElseThrow(() ->
                new EnrollmentNotFoundException("Enrollment with ID " + id + " not found"));
    }

    public List<UserDTO> getUsersByCourseId(UUID courseId) {
        return enrollmentRepository.findUsersByCourseId(courseId)
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByStudentId(UUID studentId) {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(enrollment -> CourseMapper.toDTO(enrollment.getCourse()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getStudentsByInstructor(UUID instructorId) {
        return enrollmentRepository.findStudentsByInstructorId(instructorId)
                .stream()
                .map(user ->  UserMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    public boolean isStudentEnrolled(UUID studentId, UUID courseId) {
        return enrollmentRepository.isStudentEnrolled(studentId, courseId);
    }

    public EnrollmentDTO enrollStudent(UUID studentId, UUID courseId) {
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

//    @Transactional
//    public String enrollUserByEmail(UUID courseId, String email) {
//        // Fetch user by email
//        UserEntity user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // Extract userId
//        UUID userId = user.getId();
//
//        // Fetch course by courseId
//        CourseEntity course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + " not found"));
//
//        // Check if the user is already enrolled
//        boolean isAlreadyEnrolled = enrollmentRepository.existsByUserUserIdAndCourseCourseId(userId, courseId);
//        if (isAlreadyEnrolled) {
//            throw new DuplicateEnrollmentException("User with email " + email + " is already enrolled in this course");
//        }
//
//        // Create new enrollment
//        EnrollmentEntity enrollment = new EnrollmentEntity();
//        enrollment.setStudent(user);
//        enrollment.setCourse(course);
//        enrollmentRepository.save(enrollment);
//
//        return "User " + email + " successfully enrolled in " + course.getTitle();
//    }

    public void deleteEnrollment(UUID id) {
        EnrollmentEntity existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment with ID " + id + " not found"));

        enrollmentRepository.delete(existingEnrollment);
    }
}
