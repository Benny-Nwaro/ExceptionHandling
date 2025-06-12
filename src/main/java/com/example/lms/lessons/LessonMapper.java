package com.example.lms.lessons;

import com.example.lms.courses.CourseEntity;

public class LessonMapper {

    public static LessonDTO toDTO(LessonEntity lesson) {
        return new LessonDTO(
                lesson.getLessonId(),
                lesson.getCourse().getCourseId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getVideoUrl(),
                lesson.getDocumentLink()
        );
    }

    public static LessonEntity toEntity(LessonDTO lessonDTO, CourseEntity course) {
        if (lessonDTO == null) {
            throw new IllegalArgumentException("LessonDTO cannot be null");
        }

        LessonEntity lesson = new LessonEntity();
        lesson.setLessonId(lessonDTO.getLessonId());
        lesson.setCourse(course);
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());
        lesson.setDocumentLink(lessonDTO.getDocumentLink());

        return lesson;
    }
}
