package com.example.lms.lessons;

import java.util.UUID;

public class LessonDTO {
    private UUID lessonId;
    private UUID courseId;
    private String title;
    private String content;

    // External video URL
    private String videoUrl;

    // External document link
    private String documentLink;

    public LessonDTO() {}

    public LessonDTO(UUID lessonId, UUID courseId, String title, String content,
                     String videoUrl, String documentLink) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.documentLink = documentLink;
    }

    public UUID getLessonId() {
        return lessonId;
    }

    public void setLessonId(UUID lessonId) {
        this.lessonId = lessonId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }
}
