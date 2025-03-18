package com.example.lms.enrollment;

import com.example.lms.courses.CourseDTO;
import com.example.lms.users.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable UUID id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/course/{courseId}/users")
    public ResponseEntity<List<UserDTO>> getUsersByCourse(@PathVariable UUID courseId) {
        return ResponseEntity.ok(enrollmentService.getUsersByCourseId(courseId));
    }

    @GetMapping("/instructor/{instructorId}/students")
    public ResponseEntity<List<UserDTO>> getStudentsByInstructor(@PathVariable UUID instructorId) {
        List<UserDTO> students = enrollmentService.getStudentsByInstructor(instructorId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudent(@PathVariable UUID studentId) {
        List<CourseDTO> courses = enrollmentService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseDTO>> getEnrolledCourses(@PathVariable UUID studentId) {
        return ResponseEntity.ok(enrollmentService.getCoursesByStudentId(studentId));
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkEnrollment(
            @RequestParam UUID studentId, @RequestParam UUID courseId) {
        boolean enrolled = enrollmentService.isStudentEnrolled(studentId, courseId);
        return ResponseEntity.ok(Map.of("enrolled", enrolled));
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody @Valid EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO savedEnrollment = enrollmentService.enrollStudent(
                enrollmentDTO.getStudentId(),
                enrollmentDTO.getCourseId()
        );
        return ResponseEntity.status(201).body(savedEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable UUID id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
