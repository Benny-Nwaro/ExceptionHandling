package com.example.lms.lessons;

import com.example.lms.courses.CourseEntity;
import com.example.lms.exceptions.LessonAlreadyExistsException;
import com.example.lms.exceptions.LessonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    public LessonEntity getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));
    }

    public LessonEntity createLesson(LessonEntity lesson) {
        // Ensure unique title per course
        Optional<LessonEntity> existingLesson = lessonRepository.findByCourseAndTitle(lesson.getCourse(), lesson.getTitle());
        if (existingLesson.isPresent()) {
            throw new LessonAlreadyExistsException("A lesson with the title '" + lesson.getTitle() + "' already exists in this course.");
        }
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new LessonNotFoundException("Lesson with ID " + id + " not found");
        }
        lessonRepository.deleteById(id);
    }
}
