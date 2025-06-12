//package com.example.lms.controllers;
//
//import com.example.lms.enrollment.EnrollmentController;
//import com.example.lms.enrollment.EnrollmentDTO;
//import com.example.lms.enrollment.EnrollmentService;
//import com.example.lms.exceptions.EnrollmentNotFoundException;
//import com.example.lms.exceptions.GlobalExceptionHandler;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class EnrollmentControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private EnrollmentService enrollmentService;
//
//    @InjectMocks
//    private EnrollmentController enrollmentController;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController)
//                .setControllerAdvice(new GlobalExceptionHandler()) // Register exception handler
//                .build();
//    }
//
//    @Test
//    void getAllEnrollments_ShouldReturnList() throws Exception {
//        when(enrollmentService.getAllEnrollments()).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/v1/enrollments"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    void getEnrollmentById_ShouldReturnEnrollment_WhenFound() throws Exception {
//        UUID id = UUID.randomUUID();
//        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
//        when(enrollmentService.getEnrollmentById(id)).thenReturn(enrollmentDTO);
//
//        mockMvc.perform(get("/api/v1/enrollments/" + id))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    void getEnrollmentById_ShouldReturnNotFound_WhenNotFound() throws Exception {
//        UUID id = UUID.randomUUID();
//
//        when(enrollmentService.getEnrollmentById(id))
//                .thenThrow(new EnrollmentNotFoundException("Enrollment not found"));
//
//        mockMvc.perform(get("/api/v1/enrollments/" + id))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Enrollment not found"))
//                .andExpect(jsonPath("$.status").value(404)); // Ensure the JSON response structure matches
//    }
//
//
//    @Test
//    void createEnrollment_ShouldReturnCreatedEnrollment() throws Exception {
//        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
//        when(enrollmentService.enrollStudent(any(), any())).thenReturn(enrollmentDTO);
//
//        mockMvc.perform(post("/api/v1/enrollments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(enrollmentDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    void deleteEnrollment_ShouldReturnNoContent() throws Exception {
//        UUID id = UUID.randomUUID();
//        doNothing().when(enrollmentService).deleteEnrollment(id);
//
//        mockMvc.perform(delete("/api/v1/enrollments/" + id))
//                .andExpect(status().isNoContent());
//    }
//}
//
