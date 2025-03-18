package com.example.lms.courses;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadCourseImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = courseService.uploadCourseImage(id, file);
            return ResponseEntity.ok(imageUrl);
        } catch (RuntimeException e) { // Now handles all failures
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
