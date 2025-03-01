package com.example.lms.lessons;

import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.LessonNotFoundException;
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
        List<LessonEntity> lessons = lessonRepository.findAll();
        return lessons.stream().map(LessonMapper::toDTO).collect(Collectors.toList());
    }

    public LessonDTO getLessonById(Long id) {
       return lessonRepository.findById(id).map(LessonMapper::toDTO)
               .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));
    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        LessonEntity lesson = LessonMapper.toEntity(lessonDTO);
        lesson.setCourse(courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + lessonDTO.getCourseId())));
        return LessonMapper.toDTO(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new LessonNotFoundException("Lesson with ID " + id + " not found");
        }
        lessonRepository.deleteById(id);
    }
}
