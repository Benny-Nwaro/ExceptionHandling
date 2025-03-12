package com.example.lms.courses;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping(params = "title")
    public ResponseEntity<CourseDTO> getCourseByTitle(@RequestParam String title) {
        return ResponseEntity.ok(courseService.getCourseByTitle(title));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<CourseDTO>> getCoursesByUserId(@PathVariable("id") UUID userId) {
        List<CourseDTO> courses = courseService.getCoursesByInstructorId(userId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
