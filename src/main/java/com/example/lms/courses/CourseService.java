package com.example.lms.courses;

import com.example.lms.exceptions.CourseAlreadyExistsException;
import com.example.lms.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + id + " does not exist"));
    }

    public CourseEntity getCourseByTitle(String title) {
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> new CourseNotFoundException("Course with title '" + title + "' does not exist"));
    }

    public CourseEntity saveCourse(CourseEntity course) {
        Optional<CourseEntity> existingCourse = courseRepository.findByTitle(course.getTitle());

        if (existingCourse.isPresent()) {
            throw new CourseAlreadyExistsException("Course with title '" + course.getTitle() + "' already exists");
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Cannot delete: Course with ID " + id + " does not exist");
        }
        courseRepository.deleteById(id);
    }
}

