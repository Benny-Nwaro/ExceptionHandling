package com.example.lms.courses;

import com.example.lms.exceptions.CourseAlreadyExistsException;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(UUID id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Course with ID %s does not exist",  id)));

        return CourseMapper.toDTO(course);
    }

    public CourseDTO getCourseByTitle(String title) {
        CourseEntity course = courseRepository.findByTitle(title)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Course with title '%s' does not exist", title)));

        return CourseMapper.toDTO(course);
    }

    public List<CourseDTO> getCoursesByInstructorId(UUID instructorId) {
        UserEntity instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        List<CourseEntity> courses = courseRepository.findByInstructorId(instructorId);
        return courses.stream()
                .map(course -> new CourseDTO(
                        course.getCourseId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        if (courseRepository.existsByTitle(courseDTO.getTitle())) {
            throw new CourseAlreadyExistsException(String
                    .format("Course with title '%s' already exists", courseDTO.getTitle()));
        }
        CourseEntity savedEntity = courseRepository.save(CourseMapper.toEntity(courseDTO));
        return CourseMapper.toDTO(savedEntity);
    }

    public void deleteCourse(UUID id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String
                        .format("Cannot delete: Course with ID %s does not exist", id)));

        courseRepository.delete(course);
    }
}
