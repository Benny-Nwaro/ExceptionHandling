//package com.example.lms.controllers;
//
//import com.example.lms.exceptions.LessonNotFoundException;
//import com.example.lms.lessons.LessonController;
//import com.example.lms.lessons.LessonDTO;
//import com.example.lms.lessons.LessonService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(LessonController.class)
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class LessonControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private LessonService lessonService;
//
//    private final UUID lessonId = UUID.randomUUID();
//    private final UUID courseId = UUID.randomUUID();
//    private final LessonDTO lessonDTO = new LessonDTO(lessonId, courseId, "Java Basics",
//            "Introduction to Java", "http://example.com/video.mp4");
//
//    @Test
//    void getAllLessons_ShouldReturnListOfLessons() throws Exception {
//        when(lessonService.getAllLessons()).thenReturn(List.of(lessonDTO));
//
//        mockMvc.perform(get("/api/v1/lessons"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].lessonId").value(lessonId.toString()))
//                .andExpect(jsonPath("$[0].title").value("Java Basics"));
//    }
//
//    @Test
//    void getLessonById_ShouldReturnLesson() throws Exception {
//        when(lessonService.getLessonById(lessonId)).thenReturn(lessonDTO);
//
//        mockMvc.perform(get("/api/v1/lessons/" + lessonId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.lessonId").value(lessonId.toString()))
//                .andExpect(jsonPath("$.title").value("Java Basics"));
//    }
//
//    @Test
//    void getLessonById_ShouldReturnNotFound_WhenLessonDoesNotExist() throws Exception {
//        when(lessonService.getLessonById(lessonId)).thenThrow(new LessonNotFoundException("Lesson not found"));
//
//        mockMvc.perform(get("/api/v1/lessons/" + lessonId))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Lesson not found"));
//    }
//
//    @Test
//    void createLesson_ShouldReturnCreatedLesson() throws Exception {
//        when(lessonService.createLesson(any(LessonDTO.class))).thenReturn(lessonDTO);
//
//        String lessonJson = """
//            {
//                "lessonId": "%s",
//                "courseId": "%s",
//                "title": "Java Basics",
//                "content": "Introduction to Java",
//                "videoUrl": "http://example.com/video.mp4"
//            }
//            """.formatted(lessonId, courseId);
//
//        mockMvc.perform(post("/api/v1/lessons")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(lessonJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.lessonId").value(lessonId.toString()))
//                .andExpect(jsonPath("$.title").value("Java Basics"));
//    }
//
//    @Test
//    void deleteLesson_ShouldReturnNoContent() throws Exception {
//        doNothing().when(lessonService).deleteLesson(lessonId);
//
//        mockMvc.perform(delete("/api/v1/lessons/" + lessonId))
//                .andExpect(status().isNoContent());
//
//        verify(lessonService, times(1)).deleteLesson(lessonId);
//    }
//}
