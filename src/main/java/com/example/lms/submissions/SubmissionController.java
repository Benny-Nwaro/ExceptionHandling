package com.example.lms.submissions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/{studentId}/{assignmentId}")
    public ResponseEntity<SubmissionDTO> submitAssignment(@RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO submission = submissionService.submitAssignment(submissionDTO);
        return ResponseEntity.ok(submission);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @GetMapping("/{submissionId}")
    public SubmissionDTO getSubmission(@PathVariable Long submissionId) {
        return   submissionService.getSubmissionById(submissionId);
    }
}
