package com.example.lms.lessons;

import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.exceptions.LessonAlreadyExistsException;
import com.example.lms.exceptions.LessonNotFoundException;
import com.example.lms.security.CloudinaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private CloudinaryService cloudinaryService;

    @Value("${file.upload-dir}") // Assuming you have a configuration for this in your application.properties
    private String uploadDir;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, CloudinaryService cloudinaryService) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.cloudinaryService = cloudinaryService;
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

    public LessonDTO createLesson(LessonDTO lessonDTO, MultipartFile document, MultipartFile video) throws IOException {
        CourseEntity course = courseRepository.findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + lessonDTO.getCourseId()));

        if (lessonRepository.findByCourseAndTitle(course, lessonDTO.getTitle()).isPresent()) {
            throw new LessonAlreadyExistsException("Lesson with title '" + lessonDTO.getTitle() + "' already exists for this course.");
        }

        // Upload document to Cloudinary
        if (document != null && !document.isEmpty()) {
            String docUrl = cloudinaryService.uploadFile(
                    document,
                    "courses/" + course.getCourseId() + "/lessons",
                    UUID.randomUUID() + "_document"
            );
            lessonDTO.setDocumentLink(docUrl);
        }

        // Upload video to Cloudinary
        if (video != null && !video.isEmpty()) {
            String videoUrl = cloudinaryService.uploadFile(
                    video,
                    "courses/" + course.getCourseId() + "/lessons",
                    UUID.randomUUID() + "_video"
            );
            lessonDTO.setVideoUrl(videoUrl);
        }

        LessonEntity lesson = LessonMapper.toEntity(lessonDTO, course);
        return LessonMapper.toDTO(lessonRepository.save(lesson));
    }

    private String storeFile(MultipartFile file, String folder) throws IOException {
        // Construct path from project root
        Path path = Paths.get(System.getProperty("user.dir"), uploadDir, folder);
        Files.createDirectories(path); // Creates directory if it doesn't exist

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationPath = path.resolve(fileName);

        file.transferTo(destinationPath.toFile());

        // Return relative path for later access
        return Paths.get(uploadDir, folder, fileName).toString();
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
