package com.example.lms.enrollment;

import com.example.lms.users.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
