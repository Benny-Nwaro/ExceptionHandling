package com.example.lms.courses;

import com.example.lms.exceptions.CourseNotFoundException;

public class CourseMapper {
    public static CourseDTO toDTO(CourseEntity course) {
        if (course == null) {
            throw new CourseNotFoundException(" Course cannot contain null values");
        }
        return new CourseDTO(
                course.getCourseId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId(),
                course.getCoursePrice(),
                course.getCourseImage()
        );
    }

    public static CourseEntity toEntity(CourseDTO courseDTO) {
        return new CourseEntity(
                null,
                courseDTO.getTitle(),
                courseDTO.getDescription(),
                courseDTO.getInstructorId(),
                courseDTO.getCoursePrice(),
                courseDTO.getCourseImage()
                );
    }
}
