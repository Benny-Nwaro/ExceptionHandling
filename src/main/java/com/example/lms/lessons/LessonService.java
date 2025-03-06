package com.example.lms.lessons;

import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.LessonAlreadyExistsException;
import com.example.lms.exceptions.LessonNotFoundException;
import com.example.lms.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public LessonDTO getLessonById(Long id) {
        return lessonRepository.findById(id)
                .map(LessonMapper::toDTO)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));
    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        // Check if course exists
        LessonEntity lesson = LessonMapper.toEntity(lessonDTO);
        lesson.setCourse(courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + lessonDTO.getCourseId())));

        if (lessonRepository.findByCourseAndTitle(lesson.getCourse(), lessonDTO.getTitle())) {
            throw new LessonAlreadyExistsException("Lesson with title '" + lessonDTO.getTitle() + "' already exists for this course.");
        }

        return LessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));

        lessonRepository.delete(lesson);
    }
}
