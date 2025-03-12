package com.example.lms.services;

import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.enrollment.EnrollmentDTO;
import com.example.lms.enrollment.EnrollmentEntity;
import com.example.lms.enrollment.EnrollmentRepository;
import com.example.lms.enrollment.EnrollmentService;
import com.example.lms.exceptions.DuplicateEnrollmentException;
import com.example.lms.exceptions.EnrollmentNotFoundException;
import com.example.lms.users.Gender;
import com.example.lms.users.Role;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private UUID enrollmentId;
    private UUID studentId;
    private UUID courseId;
    private UserEntity student;
    private CourseEntity course;
    private EnrollmentEntity enrollmentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        enrollmentId = UUID.randomUUID();

        student = new UserEntity(
                studentId,
                "John", "Doe",
                "john@example.com",
                "password123",
                "+1234567890",
                new Date(),  // Date of birth
                Role.STUDENT, // Assuming Role is an enum
                Gender.MALE, // Assuming Gender is an enum
                "Aspiring software engineer",
                25
        );

        course = new CourseEntity(courseId, "Spring Boot", "Spring Boot Basics", UUID.randomUUID());

        enrollmentEntity = new EnrollmentEntity(enrollmentId, student, course);
    }


    @Test
    void shouldReturnAllEnrollments() {
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollmentEntity));

        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();

        assertEquals(1, enrollments.size());
        assertEquals(studentId, enrollments.get(0).getStudentId());
        assertEquals(courseId, enrollments.get(0).getCourseId());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEnrollmentById() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollmentEntity));

        EnrollmentDTO result = enrollmentService.getEnrollmentById(enrollmentId);

        assertNotNull(result);
        assertEquals(enrollmentId, result.getEnrollmentId());
        verify(enrollmentRepository, times(1)).findById(enrollmentId);
    }

    @Test
    void shouldThrowExceptionIfEnrollmentNotFoundById() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.getEnrollmentById(enrollmentId));
        verify(enrollmentRepository, times(1)).findById(enrollmentId);
    }

    @Test
    void shouldEnrollStudentInCourse() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());
        when(enrollmentRepository.save(any(EnrollmentEntity.class))).thenReturn(enrollmentEntity);

        EnrollmentDTO savedEnrollment = enrollmentService.enrollStudent(studentId, courseId);

        assertNotNull(savedEnrollment);
        assertEquals(studentId, savedEnrollment.getStudentId());
        assertEquals(courseId, savedEnrollment.getCourseId());
        verify(userRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(enrollmentRepository, times(1)).save(any(EnrollmentEntity.class));
    }

    @Test
    void shouldThrowExceptionIfStudentNotFound() {
        when(userRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.enrollStudent(studentId, courseId));
        verify(userRepository, times(1)).findById(studentId);
        verify(courseRepository, never()).findById(any());
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionIfCourseNotFound() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.enrollStudent(studentId, courseId));
        verify(userRepository, times(1)).findById(studentId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionIfStudentIsAlreadyEnrolled() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.of(enrollmentEntity));

        assertThrows(DuplicateEnrollmentException.class, () -> enrollmentService.enrollStudent(studentId, courseId));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteEnrollmentById() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollmentEntity));

        enrollmentService.deleteEnrollment(enrollmentId);

        verify(enrollmentRepository, times(1)).delete(enrollmentEntity);
    }

    @Test
    void shouldThrowExceptionIfDeletingNonExistentEnrollment() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.deleteEnrollment(enrollmentId));
        verify(enrollmentRepository, never()).delete(any());
    }
}
