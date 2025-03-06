package com.example.lms.courses;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping(params = "title")
    public ResponseEntity<CourseDTO> getCourseByTitle(@RequestParam String title) {
        return ResponseEntity.ok(courseService.getCourseByTitle(title));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id); // If not found, exception is handled globally
        return ResponseEntity.noContent().build();
    }
}
