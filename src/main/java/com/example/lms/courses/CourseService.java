package com.example.lms.courses;

import com.example.lms.exceptions.CourseAlreadyExistsException;
import com.example.lms.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Course with ID %d does not exist", id)));

        return CourseMapper.toDTO(course);
    }

    public CourseDTO getCourseByTitle(String title) {
        CourseEntity course = courseRepository.findByTitle(title)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Course with title '%s' does not exist", title)));

        return CourseMapper.toDTO(course);
    }

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        if (courseRepository.existsByTitle(courseDTO.getTitle())) {
            throw new CourseAlreadyExistsException(String
                    .format("Course with title '%s' already exists", courseDTO.getTitle()));
        }
        CourseEntity savedEntity = courseRepository.save(CourseMapper.toEntity(courseDTO));
        return CourseMapper.toDTO(savedEntity);
    }

    public void deleteCourse(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Cannot delete: Course with ID %d does not exist", id)));

        courseRepository.delete(course);
    }
}
