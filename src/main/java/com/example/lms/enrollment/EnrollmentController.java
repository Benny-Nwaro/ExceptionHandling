package com.example.lms.enrollment;

import com.example.lms.exceptions.DuplicateEnrollmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentEntity>> getAllEnrollments() {
        List<EnrollmentEntity> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentEntity> getEnrollmentById(@PathVariable Long id) {
        EnrollmentEntity enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody EnrollmentEntity enrollment) {
        try {
            EnrollmentEntity savedEnrollment = enrollmentService.enrollStudent(enrollment.getStudent().getId(),
                    enrollment.getCourse().getCourseId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
        } catch (DuplicateEnrollmentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
