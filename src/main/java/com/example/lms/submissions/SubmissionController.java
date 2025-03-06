package com.example.lms.submissions;

import com.example.lms.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubmissionDTO>> submitAssignment(@RequestBody @Valid SubmissionDTO submissionDTO) {
        SubmissionDTO submission = submissionService.submitAssignment(submissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(submission, "Submission created successfully"));
    }

    @GetMapping
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(submissionService.getAllSubmissions(page, size));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDTO> getSubmission(@PathVariable Long submissionId) {
        SubmissionDTO submission = submissionService.getSubmissionById(submissionId);
        return ResponseEntity.ok(submission);
    }
}