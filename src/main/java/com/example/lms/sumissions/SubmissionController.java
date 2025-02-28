package com.example.lms.sumissions;

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

    @PostMapping("/{studentId}/{assignmentId}")
    public ResponseEntity<SubmissionEntity> submitAssignment(@PathVariable Long studentId,
                                                             @PathVariable Long assignmentId) {
        SubmissionEntity submission = submissionService.submitAssignment(studentId, assignmentId);
        return ResponseEntity.ok(submission);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionEntity>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionEntity> getSubmission(@PathVariable Long submissionId) {
        Optional<SubmissionEntity> submission = submissionService.getSubmissionById(submissionId);
        return submission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
