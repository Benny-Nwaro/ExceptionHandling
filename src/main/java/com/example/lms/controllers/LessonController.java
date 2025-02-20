package com.example.lms.controllers;

import com.example.lms.entities.LessonEntity;
import com.example.lms.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<LessonEntity> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonEntity> getLessonById(@PathVariable Long id) {
        Optional<LessonEntity> lesson = Optional.ofNullable(lessonService.getLessonById(id));
        return lesson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public LessonEntity createLesson(@RequestBody LessonEntity lesson) {
        return lessonService.saveLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}

