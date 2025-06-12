package com.example.lms.controllers;

import com.example.lms.courses.CourseController;
import com.example.lms.courses.CourseDTO;
import com.example.lms.courses.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    private CourseDTO courseDTO;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        courseDTO = new CourseDTO(courseId, "Java Basics", "Introduction to Java",
                UUID.randomUUID(), new BigDecimal("99.99"), "course-image-url.jpg");
    }

    @Test
    void getAllCourses_ShouldReturnListOfCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(Collections.singletonList(courseDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java Basics"));
    }

    @Test
    void getCourseById_ShouldReturnCourse() throws Exception {
        when(courseService.getCourseById(courseId)).thenReturn(courseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    void getCourseByTitle_ShouldReturnCourse() throws Exception {
        when(courseService.getCourseByTitle("Java Basics")).thenReturn(courseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses")
                        .param("title", "Java Basics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        when(courseService.saveCourse(any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java Basics"))
                .andExpect(jsonPath("$.coursePrice").value(99.99))
                .andExpect(jsonPath("$.courseImage").value("course-image-url.jpg"));
    }

    @Test
    void deleteCourse_ShouldReturnNoContent() throws Exception {
        doNothing().when(courseService).deleteCourse(courseId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/courses/{id}", courseId))
                .andExpect(status().isNoContent());
    }
}
