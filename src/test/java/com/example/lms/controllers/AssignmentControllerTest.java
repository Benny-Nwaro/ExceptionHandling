package com.example.lms.controllers;

import com.example.lms.assignments.AssignmentController;
import com.example.lms.assignments.AssignmentDTO;
import com.example.lms.assignments.AssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssignmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignmentController).build();
        objectMapper.registerModule(new JavaTimeModule());

    }

    @Test
    void getAssignments_ShouldReturnList() throws Exception {
        when(assignmentService.getAssignments()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/assignments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAssignmentById_ShouldReturnAssignment_WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        when(assignmentService.getAssignmentById(id)).thenReturn(assignmentDTO);

        mockMvc.perform(get("/api/v1/assignments/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAssignmentById_ShouldReturnNotFound_WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(assignmentService.getAssignmentById(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/assignments/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAssignment_ShouldReturnCreatedAssignment() throws Exception {
        AssignmentDTO assignmentDTO = new AssignmentDTO(
                UUID.randomUUID(), // Assignment ID (optional)
                "Test Assignment", // Non-blank title
                "This is a test assignment.", // Non-blank description
                UUID.randomUUID(), // Valid Course ID
                LocalDate.now().plusDays(7) // Future due date
        );

        when(assignmentService.saveAssignment(any(AssignmentDTO.class))).thenReturn(assignmentDTO);

        mockMvc.perform(post("/api/v1/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO)))
                .andExpect(status().isCreated()) // Expected 201 Created instead of 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void deleteAssignment_ShouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(assignmentService).deleteById(id);

        mockMvc.perform(delete("/api/v1/assignments/" + id))
                .andExpect(status().isNoContent());
    }
}

