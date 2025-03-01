package com.example.lms.lessons;

import com.example.lms.exceptions.LessonAlreadyExistsException;
import com.example.lms.exceptions.LessonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }

    @PostMapping
    public ResponseEntity<?> createLesson(@RequestBody LessonDTO lesson) {
        try {
            LessonDTO createdLesson = lessonService.createLesson(lesson);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
        } catch (LessonAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        } catch (LessonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
