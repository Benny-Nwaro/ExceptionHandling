package com.example.lms.lessons;

public class LessonMapper {

    public static LessonDTO toDTO(LessonEntity lesson) {
        return new LessonDTO(
                lesson.getLessonId(),
                lesson.getCourse().getCourseId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getVideoUrl()
        );
    }

    public static LessonEntity toEntity(LessonDTO lessonDTO) {
        LessonEntity lesson = new LessonEntity();
        lesson.setLessonId(lessonDTO.getLessonId());
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());
        return lesson;
    }
}
