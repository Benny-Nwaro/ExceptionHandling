package com.example.lms.assignments;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id){
        Optional<AssignmentDTO> assignment = Optional.ofNullable(assignmentService.getAssignmentById(id));
        return assignment.map(ResponseEntity ::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity <AssignmentDTO> createAssignment(@RequestBody @Valid AssignmentDTO assignment){
        return ResponseEntity.ok(assignmentService.saveAssignment(assignment));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment (@PathVariable Long id){
        assignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
