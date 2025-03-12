package com.example.lms.assignments;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAssignments(){
        return ResponseEntity.ok(assignmentService.getAssignments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable UUID id){
        Optional<AssignmentDTO> assignment = Optional.ofNullable(assignmentService.getAssignmentById(id));
        return assignment.map(ResponseEntity ::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO createdAssignment = assignmentService.saveAssignment(assignmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment (@PathVariable UUID id){
        assignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
