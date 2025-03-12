package com.example.lms.services;

import com.example.lms.assignments.AssignmentDTO;
import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.assignments.AssignmentRepository;
import com.example.lms.assignments.AssignmentService;
import com.example.lms.courses.CourseEntity;
import com.example.lms.courses.CourseRepository;
import com.example.lms.exceptions.AssignmentNotFoundException;
import com.example.lms.exceptions.CourseNotFoundException;
import com.example.lms.exceptions.DuplicateAssignmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private UUID assignmentId;
    private UUID courseId;
    private CourseEntity courseEntity;
    private AssignmentEntity assignmentEntity;
    private AssignmentDTO assignmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        courseId = UUID.randomUUID();
        assignmentId = UUID.randomUUID();
        courseEntity = new CourseEntity(courseId, "Java Basics", "Intro to Java", UUID.randomUUID());
        assignmentEntity = new AssignmentEntity(assignmentId, "Project 1", "Build a simple app", courseEntity, null);
        assignmentDTO = new AssignmentDTO(assignmentId, "Project 1", "Build a simple app", courseId, null);
    }

    @Test
    void shouldReturnAllAssignments() {
        when(assignmentRepository.findAll()).thenReturn(List.of(assignmentEntity));

        List<AssignmentDTO> assignments = assignmentService.getAssignments();

        assertEquals(1, assignments.size());
        assertEquals("Project 1", assignments.get(0).getTitle());
        verify(assignmentRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnAssignmentById() {
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignmentEntity));

        AssignmentDTO result = assignmentService.getAssignmentById(assignmentId);

        assertNotNull(result);
        assertEquals(assignmentId, result.getAssignmentId());
        verify(assignmentRepository, times(1)).findById(assignmentId);
    }

    @Test
    void shouldThrowExceptionIfAssignmentNotFoundById() {
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(AssignmentNotFoundException.class, () -> assignmentService.getAssignmentById(assignmentId));
        verify(assignmentRepository, times(1)).findById(assignmentId);
    }

    @Test
    void shouldSaveNewAssignment() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(assignmentRepository.existsByTitle("Project 1")).thenReturn(false);
        when(assignmentRepository.save(any(AssignmentEntity.class))).thenReturn(assignmentEntity);

        AssignmentDTO savedAssignment = assignmentService.saveAssignment(assignmentDTO);

        assertNotNull(savedAssignment);
        assertEquals("Project 1", savedAssignment.getTitle());
        verify(courseRepository, times(1)).findById(courseId);
        verify(assignmentRepository, times(1)).existsByTitle("Project 1");
        verify(assignmentRepository, times(1)).save(any(AssignmentEntity.class));
    }

    @Test
    void shouldThrowExceptionIfCourseNotFound() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> assignmentService.saveAssignment(assignmentDTO));
        verify(courseRepository, times(1)).findById(courseId);
        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionIfAssignmentAlreadyExists() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(assignmentRepository.existsByTitle("Project 1")).thenReturn(true);

        assertThrows(DuplicateAssignmentException.class, () -> assignmentService.saveAssignment(assignmentDTO));
        verify(courseRepository, times(1)).findById(courseId);
        verify(assignmentRepository, times(1)).existsByTitle("Project 1");
        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteAssignmentById() {
        when(assignmentRepository.existsById(assignmentId)).thenReturn(true);

        assignmentService.deleteById(assignmentId);

        verify(assignmentRepository, times(1)).deleteById(assignmentId);
    }

    @Test
    void shouldThrowExceptionIfDeletingNonExistentAssignment() {
        when(assignmentRepository.existsById(assignmentId)).thenReturn(false);

        assertThrows(AssignmentNotFoundException.class, () -> assignmentService.deleteById(assignmentId));
        verify(assignmentRepository, times(1)).existsById(assignmentId);
        verify(assignmentRepository, never()).deleteById(any());
    }
}
