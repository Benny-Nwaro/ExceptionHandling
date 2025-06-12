package com.example.lms.controllers;

import com.example.lms.exceptions.GlobalExceptionHandler;
import com.example.lms.exceptions.ResourceNotFoundException;
import com.example.lms.submissions.SubmissionController;
import com.example.lms.submissions.SubmissionDTO;
import com.example.lms.submissions.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // Enables Mockito support
class SubmissionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubmissionService submissionService; // Use @Mock instead of @MockBean

    @InjectMocks
    private SubmissionController submissionController; // Inject mock dependencies

    private final ObjectMapper objectMapper = new ObjectMapper(); // Converts Java objects to JSON

    private SubmissionDTO submissionDTO;
    private UUID submissionId;

    @BeforeEach
    void setUp() {
        // Register JavaTimeModule to handle LocalDateTime serialization
        objectMapper.registerModule(new JavaTimeModule());

        // Manually initialize MockMvc with exception handling
        mockMvc = MockMvcBuilders.standaloneSetup(submissionController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Handles exceptions in tests
                .build();

        submissionId = UUID.randomUUID();
        submissionDTO = new SubmissionDTO(
                submissionId,
                UUID.randomUUID(),  // studentId
                UUID.randomUUID(),  // assignmentId
                LocalDateTime.now()
        );
    }

    @Test
    void submitAssignment_Success() throws Exception {
        SubmissionDTO submissionDTO = new SubmissionDTO(UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), LocalDateTime.now());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(submissionDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Response: " + result.getResponse().getContentAsString());
    }


    @Test
    void getSubmissionById_Success() throws Exception {
        when(submissionService.getSubmissionById(submissionId)).thenReturn(submissionDTO);

        mockMvc.perform(get("/api/v1/submissions/{id}", submissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.submissionId").value(submissionId.toString()));

        verify(submissionService, times(1)).getSubmissionById(submissionId);
    }

    @Test
    void getSubmissionById_NotFound() throws Exception {
        when(submissionService.getSubmissionById(submissionId))
                .thenThrow(new ResourceNotFoundException("Submission not found"));

        mockMvc.perform(get("/api/v1/submissions/{id}", submissionId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Submission not found"));

        verify(submissionService, times(1)).getSubmissionById(submissionId);
    }
}
