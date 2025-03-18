package com.example.lms.lessons;

import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.exceptions.LessonAlreadyExistsException;
import com.example.lms.exceptions.LessonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(LessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonById(UUID id) {
        return lessonRepository.findById(id)
                .map(LessonMapper::toDTO)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));
    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        // Fetch the course
        CourseEntity course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + lessonDTO.getCourseId()));

        // Check if a lesson with the same title already exists for this course
        if (lessonRepository.findByCourseAndTitle(course, lessonDTO.getTitle()).isPresent()) {
            throw new LessonAlreadyExistsException("Lesson with title '" + lessonDTO.getTitle() + "' already exists for this course.");
        }

        // Map DTO to Entity and set the course
        LessonEntity lesson = LessonMapper.toEntity(lessonDTO, course);

        // Save lesson and return DTO
        return LessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public List<LessonDTO> getLessonsByCourseId(UUID courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        return lessonRepository.findByCourse(course)
                .stream()
                .map(LessonMapper::toDTO)
                .collect(Collectors.toList());
    }



    public void deleteLesson(UUID id) {
        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));

        lessonRepository.delete(lesson);
    }
}
