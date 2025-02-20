package com.example.lms.controllers;


import com.example.lms.entities.SubmissionEntity;
import com.example.lms.services.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SubmissionEntity>> getSubmissionsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByStudent(studentId));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionEntity>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByAssignment(assignmentId));
    }

    @PostMapping
    public ResponseEntity<SubmissionEntity> submitAssignment(@RequestBody SubmissionEntity submission) {
        return ResponseEntity.ok(submissionService.submitAssignment(submission));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionEntity> getSubmission(@PathVariable Long id) {
        Optional<SubmissionEntity> submission = submissionService.getSubmissionById(id);
        return submission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
