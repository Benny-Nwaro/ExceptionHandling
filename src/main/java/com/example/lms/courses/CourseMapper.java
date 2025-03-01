package com.example.lms.courses;

public class CourseMapper {
    public static CourseDTO toDTO(CourseEntity course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId()
        );
    }
}
