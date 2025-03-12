package com.example.lms.services;

import com.example.lms.courses.*;
import com.example.lms.exceptions.CourseAlreadyExistsException;
import com.example.lms.exceptions.CourseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private UUID courseId;
    private UUID instructorId;
    private CourseEntity courseEntity;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        instructorId = UUID.randomUUID(); // Required field for CourseEntity

        courseEntity = new CourseEntity(courseId, "Java Basics", "Intro to Java", instructorId);
        courseDTO = new CourseDTO(courseId, "Java Basics", "Intro to Java", instructorId);
    }

    @Test
    void shouldReturnAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(courseEntity));

        try (MockedStatic<CourseMapper> mockedMapper = mockStatic(CourseMapper.class)) {
            mockedMapper.when(() -> CourseMapper.toDTO(courseEntity)).thenReturn(courseDTO);

            List<CourseDTO> courses = courseService.getAllCourses();

            assertEquals(1, courses.size());
            assertEquals("Java Basics", courses.get(0).getTitle());
            verify(courseRepository, times(1)).findAll();
            mockedMapper.verify(() -> CourseMapper.toDTO(courseEntity), times(1)); // Verify mapper call
        }
    }

    @Test
    void shouldReturnCourseById() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        try (MockedStatic<CourseMapper> mockedMapper = mockStatic(CourseMapper.class)) {
            mockedMapper.when(() -> CourseMapper.toDTO(courseEntity)).thenReturn(courseDTO);

            CourseDTO result = courseService.getCourseById(courseId);

            assertNotNull(result);
            assertEquals(courseId, result.getCourseId());
            verify(courseRepository, times(1)).findById(courseId);
            mockedMapper.verify(() -> CourseMapper.toDTO(courseEntity), times(1));
        }
    }

    @Test
    void shouldThrowExceptionIfCourseNotFoundById() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void shouldReturnCourseByTitle() {
        when(courseRepository.findByTitle("Java Basics")).thenReturn(Optional.of(courseEntity));

        try (MockedStatic<CourseMapper> mockedMapper = mockStatic(CourseMapper.class)) {
            mockedMapper.when(() -> CourseMapper.toDTO(courseEntity)).thenReturn(courseDTO);

            CourseDTO result = courseService.getCourseByTitle("Java Basics");

            assertNotNull(result);
            assertEquals("Java Basics", result.getTitle());
            verify(courseRepository, times(1)).findByTitle("Java Basics");
            mockedMapper.verify(() -> CourseMapper.toDTO(courseEntity), times(1));
        }
    }

    @Test
    void shouldThrowExceptionIfCourseNotFoundByTitle() {
        when(courseRepository.findByTitle("Python Basics")).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseByTitle("Python Basics"));
        verify(courseRepository, times(1)).findByTitle("Python Basics");
    }

    @Test
    void shouldSaveNewCourse() {
        when(courseRepository.existsByTitle("Java Basics")).thenReturn(false);
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);

        try (MockedStatic<CourseMapper> mockedMapper = mockStatic(CourseMapper.class)) {
            mockedMapper.when(() -> CourseMapper.toEntity(courseDTO)).thenReturn(courseEntity);
            mockedMapper.when(() -> CourseMapper.toDTO(courseEntity)).thenReturn(courseDTO);

            CourseDTO savedCourse = courseService.saveCourse(courseDTO);

            assertNotNull(savedCourse);
            assertEquals("Java Basics", savedCourse.getTitle());
            verify(courseRepository, times(1)).existsByTitle("Java Basics");
            verify(courseRepository, times(1)).save(any(CourseEntity.class));
            mockedMapper.verify(() -> CourseMapper.toEntity(courseDTO), times(1));
            mockedMapper.verify(() -> CourseMapper.toDTO(courseEntity), times(1));
        }
    }

    @Test
    void shouldThrowExceptionIfCourseAlreadyExists() {
        when(courseRepository.existsByTitle("Java Basics")).thenReturn(true);

        assertThrows(CourseAlreadyExistsException.class, () -> courseService.saveCourse(courseDTO));
        verify(courseRepository, times(1)).existsByTitle("Java Basics");
        verify(courseRepository, never()).save(any());
    }

    @Test
    void shouldDeleteCourseById() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).delete(courseEntity);
    }

    @Test
    void shouldThrowExceptionIfDeletingNonExistentCourse() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(courseId));
        verify(courseRepository, never()).delete(any());
    }
}
