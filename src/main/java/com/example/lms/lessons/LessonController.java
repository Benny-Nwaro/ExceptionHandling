package com.example.lms.lessons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons")
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
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable UUID id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(
            @RequestParam("courseId") UUID courseId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "videoUrl", required = false) String videoUrl,
            @RequestParam(value = "documentLink", required = false) String documentLink,
            @RequestParam(value = "video", required = false) MultipartFile videoFile,
            @RequestParam(value = "pdf", required = false) MultipartFile pdfFile
    ) throws IOException {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setCourseId(courseId);
        lessonDTO.setTitle(title);
        lessonDTO.setContent(content);
        lessonDTO.setVideoUrl(videoUrl);
        lessonDTO.setDocumentLink(documentLink);

        LessonDTO createdLesson = lessonService.createLesson(lessonDTO, pdfFile, videoFile);
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
