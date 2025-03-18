package com.example.lms.courses;

import com.example.lms.exceptions.CourseAlreadyExistsException;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.users.UserRepository;
import com.example.lms.utils.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
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
        List<CourseEntity> courses = courseRepository.findByInstructorId(instructorId);
        return courses.stream()
                .map(course -> new CourseDTO(
                        course.getCourseId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getInstructorId(),
                        course.getCoursePrice(),
                        course.getCourseImage()
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

    public String uploadCourseImage(UUID courseId, MultipartFile file) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + " not found"));

        try {
            String filePath = fileStorageService.saveFile(file, courseId);
            course.setCourseImage(filePath);
            courseRepository.save(course);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image for course ID " + courseId, e);
        }
    }


    public CourseDTO updateCourse(UUID courseId, CourseDTO courseDTO) {
        CourseEntity existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + " not found"));

        if (courseDTO.getTitle() == null) {
            throw new IllegalArgumentException("Course title cannot be null");
        }

        Optional<CourseEntity> duplicateCourse = courseRepository.findByTitle(courseDTO.getTitle());

        if (duplicateCourse.isPresent() && !duplicateCourse.get().getCourseId().equals(courseId)) {
            throw new CourseAlreadyExistsException("Course with title '" + courseDTO.getTitle() + "' already exists.");
        }

        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setCoursePrice(courseDTO.getCoursePrice());

        CourseEntity updatedCourse = courseRepository.save(existingCourse);

        return CourseMapper.toDTO(updatedCourse);
    }


    public void deleteCourse(UUID id) {
        courseRepository.findById(id).ifPresentOrElse(
                courseRepository::delete,
                () -> { throw new CourseNotFoundException("Cannot delete: Course with ID " + id + " does not exist"); }
        );
    }

}
