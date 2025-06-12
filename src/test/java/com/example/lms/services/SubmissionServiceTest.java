package com.example.lms.services;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.assignments.AssignmentRepository;
import com.example.lms.exceptions.DuplicateSubmissionException;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.submissions.SubmissionDTO;
import com.example.lms.submissions.SubmissionEntity;
import com.example.lms.submissions.SubmissionRepository;
import com.example.lms.submissions.SubmissionService;
import com.example.lms.users.UserEntity;
import com.example.lms.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private SubmissionService submissionService;

    private UUID studentId;
    private UUID assignmentId;
    private SubmissionDTO submissionDTO;
    private UserEntity student;
    private AssignmentEntity assignment;
    private SubmissionEntity submission;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        assignmentId = UUID.randomUUID();
        submissionDTO = new SubmissionDTO(UUID.randomUUID(), studentId, assignmentId, LocalDateTime.now());
        student = new UserEntity();
        student.setId(studentId);
        assignment = new AssignmentEntity();
        assignment.setAssignmentId(assignmentId);
        submission = new SubmissionEntity(student, assignment);
    }

    @Test
    void submitAssignment_Success() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(submissionRepository.existsByStudentAndAssignment(student, assignment)).thenReturn(false);
        when(submissionRepository.save(any())).thenReturn(submission);

        SubmissionDTO result = submissionService.submitAssignment(submissionDTO);

        assertNotNull(result);
        assertEquals(submissionDTO.getStudentId(), result.getStudentId());
        assertEquals(submissionDTO.getAssignmentId(), result.getAssignmentId());
        verify(submissionRepository, times(1)).save(any());
    }

    @Test
    void submitAssignment_StudentNotFound() {
        when(userRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                submissionService.submitAssignment(submissionDTO));

        assertEquals("Student not found with ID: " + studentId, exception.getMessage());
    }

    @Test
    void submitAssignment_AssignmentNotFound() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                submissionService.submitAssignment(submissionDTO));

        assertEquals("Assignment not found with ID: " + assignmentId, exception.getMessage());
    }

    @Test
    void submitAssignment_DuplicateSubmission() {
        when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(submissionRepository.existsByStudentAndAssignment(student, assignment)).thenReturn(true);

        Exception exception = assertThrows(DuplicateSubmissionException.class, () ->
                submissionService.submitAssignment(submissionDTO));

        assertEquals("Student ID " + studentId + " has already submitted Assignment ID " + assignmentId,
                exception.getMessage());
    }

    @Test
    void getAllSubmissions_Success() {
        List<SubmissionEntity> submissions = List.of(submission);
        Page<SubmissionEntity> submissionsPage = new PageImpl<>(submissions);

        when(submissionRepository.findAll(any(Pageable.class))).thenReturn(submissionsPage);

        Page<SubmissionDTO> result = submissionService.getAllSubmissions(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(submissionRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void getSubmissionById_Success() {
        when(submissionRepository.findById(submission.getSubmission_id())).thenReturn(Optional.of(submission));

        SubmissionDTO result = submissionService.getSubmissionById(submission.getSubmission_id());

        assertNotNull(result);
        assertEquals(submission.getSubmission_id(), result.getSubmissionId());
    }

    @Test
    void getSubmissionById_NotFound() {
        when(submissionRepository.findById(submission.getSubmission_id())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                submissionService.getSubmissionById(submission.getSubmission_id()));

        assertEquals("Submission not found", exception.getMessage());
    }
}
