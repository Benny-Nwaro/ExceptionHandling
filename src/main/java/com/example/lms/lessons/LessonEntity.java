package com.example.lms.lessons;


import com.example.lms.courses.CourseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "lessons")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID lessonId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String videoUrl;

    public LessonEntity() {}

    public LessonEntity(UUID lessonId, CourseEntity course, String title, String content, String videoUrl) {
        this.lessonId = lessonId;
        this.course = course;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
    }

    public UUID getLessonId() { return lessonId; }
    public void setLessonId(UUID lessonId) { this.lessonId = lessonId; }

    public CourseEntity getCourse() { return course; }
    public void setCourse(CourseEntity course) { this.course = course; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", course=" + (course.getCourseId()) +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}

