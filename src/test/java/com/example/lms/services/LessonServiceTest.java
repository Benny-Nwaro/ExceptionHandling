package com.example.lms.services;

import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.LessonNotFoundException;
import com.example.lms.lessons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonService lessonService;

    private UUID lessonId;
    private UUID courseId;
    private CourseEntity course;
    private LessonEntity lesson;
    private LessonDTO lessonDTO;

    @BeforeEach
    void setUp() {
        lessonId = UUID.randomUUID();
        courseId = UUID.randomUUID();

        course = new CourseEntity(courseId, "Java Basics", "Intro to Java", UUID.randomUUID(), BigDecimal.ZERO, null);
        lesson = new LessonEntity(lessonId, course, "Introduction", "Lesson description", "http://video.url");

        lessonDTO = new LessonDTO(lessonId, courseId, "Introduction", "Lesson description", "http://video.url");
    }

    @Test
    void shouldReturnAllLessons() {
        List<LessonEntity> lessons = List.of(lesson);
        when(lessonRepository.findAll()).thenReturn(lessons);

        List<LessonDTO> result = lessonService.getAllLessons();

        assertEquals(1, result.size());
        assertEquals(lesson.getTitle(), result.get(0).getTitle());
        assertEquals(lesson.getContent(), result.get(0).getContent());
        assertEquals(lesson.getVideoUrl(), result.get(0).getVideoUrl());
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnLessonById() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        LessonDTO result = lessonService.getLessonById(lessonId);

        assertEquals(lesson.getTitle(), result.getTitle());
        assertEquals(lesson.getContent(), result.getContent());
        assertEquals(lesson.getVideoUrl(), result.getVideoUrl());
        verify(lessonRepository, times(1)).findById(lessonId);
    }

    @Test
    void shouldThrowExceptionIfLessonNotFound() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.getLessonById(lessonId));

        verify(lessonRepository, times(1)).findById(lessonId);
    }

    @Test
    void shouldCreateLesson() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.findByCourseAndTitle(course, lessonDTO.getTitle())).thenReturn(Optional.empty());
        when(lessonRepository.save(any(LessonEntity.class))).thenReturn(lesson);

        LessonDTO expectedDTO = LessonMapper.toDTO(lesson);

        LessonDTO result = lessonService.createLesson(lessonDTO);

        assertEquals(expectedDTO.getTitle(), result.getTitle());
        assertEquals(expectedDTO.getContent(), result.getContent());
        assertEquals(expectedDTO.getVideoUrl(), result.getVideoUrl());

        verify(courseRepository, times(1)).findById(courseId);
        verify(lessonRepository, times(1)).save(any(LessonEntity.class));
    }

    @Test
    void shouldDeleteLesson() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        lessonService.deleteLesson(lessonId);

        verify(lessonRepository, times(1)).delete(lesson);
    }

    @Test
    void shouldThrowExceptionIfLessonNotFoundForDeletion() {
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.deleteLesson(lessonId));

        verify(lessonRepository, times(1)).findById(lessonId);
        verify(lessonRepository, never()).delete(any());
    }
}
