//package com.example.lms.services;
//
//import com.example.lms.courses.*;
//import com.example.lms.exceptions.CourseAlreadyExistsException;
//import com.example.lms.exceptions.CourseNotFoundException;
//import com.example.lms.users.UserRepository;
//import com.example.lms.utils.FileStorageService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CourseServiceTest {
//
//    @Mock
//    private CourseRepository courseRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private FileStorageService fileStorageService;
//
//    @InjectMocks
//    private CourseService courseService;
//
//    private CourseEntity courseEntity;
//    private CourseDTO courseDTO;
//    private UUID courseId;
//
//    @BeforeEach
//    void setUp() {
//        courseId = UUID.randomUUID();
//        courseEntity = new CourseEntity();
//        courseEntity.setCourseId(courseId);
//        courseEntity.setTitle("Java Programming");
//        courseEntity.setDescription("Learn Java from scratch");
//        courseEntity.setInstructorId(UUID.randomUUID());
//        courseEntity.setCoursePrice(BigDecimal.valueOf(100.0));
//        courseEntity.setCourseImage("path/to/image.jpg");
//
//        courseDTO = CourseMapper.toDTO(courseEntity);
//    }
//
//    @Test
//    void testGetAllCourses() {
//        when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
//
//        List<CourseDTO> courses = courseService.getAllCourses();
//
//        assertFalse(courses.isEmpty());
//        assertEquals(1, courses.size());
//        assertEquals("Java Programming", courses.get(0).getTitle());
//        verify(courseRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetCourseById_Success() {
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
//
//        CourseDTO result = courseService.getCourseById(courseId);
//
//        assertNotNull(result);
//        assertEquals(courseId, result.getCourseId());
//        verify(courseRepository, times(1)).findById(courseId);
//    }
//
//    @Test
//    void testGetCourseById_NotFound() {
//        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
//
//        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
//
//        verify(courseRepository, times(1)).findById(courseId);
//    }
//
//    @Test
//    void testGetCourseByTitle_Success() {
//        when(courseRepository.findByTitle("Java Programming")).thenReturn(Optional.of(courseEntity));
//
//        CourseDTO result = courseService.getCourseByTitle("Java Programming");
//
//        assertNotNull(result);
//        assertEquals("Java Programming", result.getTitle());
//        verify(courseRepository, times(1)).findByTitle("Java Programming");
//    }
//
//    @Test
//    void testGetCourseByTitle_NotFound() {
//        when(courseRepository.findByTitle("Python Programming")).thenReturn(Optional.empty());
//
//        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseByTitle("Python Programming"));
//
//        verify(courseRepository, times(1)).findByTitle("Python Programming");
//    }
//
//    @Test
//    void testSaveCourse_Success() {
//        when(courseRepository.existsByTitle(courseDTO.getTitle())).thenReturn(false);
//        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
//
//        CourseDTO savedCourse = courseService.saveCourse(courseDTO);
//
//        assertNotNull(savedCourse);
//        assertEquals(courseDTO.getTitle(), savedCourse.getTitle());
//        verify(courseRepository, times(1)).save(any(CourseEntity.class));
//    }
//
//    @Test
//    void testSaveCourse_AlreadyExists() {
//        when(courseRepository.existsByTitle(courseDTO.getTitle())).thenReturn(true);
//
//        assertThrows(CourseAlreadyExistsException.class, () -> courseService.saveCourse(courseDTO));
//
//        verify(courseRepository, never()).save(any(CourseEntity.class));
//    }
//
//    @Test
//    void testUploadCourseImage_Success() throws IOException {
//        MultipartFile mockFile = mock(MultipartFile.class);
//        lenient().when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
//        when(fileStorageService.saveFile(mockFile, courseId)).thenReturn("path/to/new/image.jpg");
//
//        String filePath = courseService.uploadCourseImage(courseId, mockFile);
//
//        assertEquals("path/to/new/image.jpg", filePath);
//        verify(fileStorageService, times(1)).saveFile(mockFile, courseId);
//        verify(courseRepository, times(1)).save(courseEntity);
//    }
//
//    @Test
//    void testUploadCourseImage_IOException() throws IOException {
//        UUID courseId = UUID.randomUUID();
//        MultipartFile mockFile = mock(MultipartFile.class);
//        CourseEntity mockCourse = new CourseEntity();
//        mockCourse.setCourseId(courseId);
//
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//        when(fileStorageService.saveFile(any(), any())).thenThrow(new IOException("File upload failed"));
//
//        RuntimeException exception = assertThrows(RuntimeException.class,
//                () -> courseService.uploadCourseImage(courseId, mockFile));
//
//        assertEquals("Failed to upload image for course ID " + courseId, exception.getMessage());
//
//        verify(courseRepository, never()).save(any());
//    }
//
//
//    @Test
//    void testUploadCourseImage_FileUploadFails() throws IOException {
//        MultipartFile mockFile = mock(MultipartFile.class);
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
//        when(fileStorageService.saveFile(mockFile, courseId)).thenThrow(new IOException("Upload failed"));
//
//        assertThrows(RuntimeException.class, () -> courseService.uploadCourseImage(courseId, mockFile));
//
//        verify(fileStorageService, times(1)).saveFile(mockFile, courseId);
//        verify(courseRepository, never()).save(any());
//    }
//
//    @Test
//    void testUpdateCourse_Success() {
//        lenient().when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
//        lenient().when(courseRepository.existsByTitle(courseDTO.getTitle())).thenReturn(false);
//        lenient().when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
//
//        CourseDTO updatedCourse = courseService.updateCourse(courseId, courseDTO);
//
//        assertNotNull(updatedCourse);
//        assertEquals(courseDTO.getTitle(), updatedCourse.getTitle());
//        verify(courseRepository, times(1)).save(courseEntity);
//    }
//
//    @Test
//    void testUpdateCourse_CourseNotFound() {
//        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
//
//        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(courseId, courseDTO));
//
//        verify(courseRepository, never()).save(any());
//    }
//
//    @Test
//    void testUpdateCourse_TitleAlreadyExists() {
//        UUID existingCourseId = UUID.randomUUID();
//        UUID instructorId = UUID.randomUUID();
//        BigDecimal coursePrice = new BigDecimal("100.0");
//        String courseImage = "default_image.png";
//
//        CourseDTO courseDTO = new CourseDTO(existingCourseId, "Java Programming", "Learn Java from scratch", instructorId, coursePrice, courseImage);
//
//        CourseEntity existingCourse = new CourseEntity();
//        existingCourse.setCourseId(existingCourseId);
//        existingCourse.setTitle("Java Programming");
//
//        CourseEntity duplicateCourse = new CourseEntity();
//        duplicateCourse.setCourseId(UUID.randomUUID());
//        duplicateCourse.setTitle("Java Programming");
//
//        when(courseRepository.findById(existingCourseId)).thenReturn(Optional.of(existingCourse));
//        when(courseRepository.findByTitle(courseDTO.getTitle())).thenReturn(Optional.of(duplicateCourse));
//
//        assertThrows(CourseAlreadyExistsException.class, () -> courseService.updateCourse(existingCourseId, courseDTO));
//
//        verify(courseRepository, never()).save(any());
//    }
//
//
//
//    @Test
//    void testDeleteCourse_Success() {
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
//
//        courseService.deleteCourse(courseId);
//
//        verify(courseRepository, times(1)).delete(courseEntity);
//    }
//
//    @Test
//    void testDeleteCourse_NotFound() {
//        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
//
//        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(courseId));
//
//        verify(courseRepository, never()).delete(any());
//    }
//}
