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

    // For storing external video links (e.g., YouTube)
    private String videoUrl;

    // For storing uploaded video filename (if user uploads a video file)
    private String videoFileName;

    // For storing external document links (e.g., Google Drive, Dropbox)
    private String documentLink;

    // For storing uploaded PDF filename (if user uploads a PDF file)
    private String pdfFileName;

    public LessonEntity() {}

    public LessonEntity(UUID lessonId, CourseEntity course, String title, String content,
                        String videoUrl, String videoFileName, String documentLink, String pdfFileName) {
        this.lessonId = lessonId;
        this.course = course;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.videoFileName = videoFileName;
        this.documentLink = documentLink;
        this.pdfFileName = pdfFileName;
    }

    public UUID getLessonId() {
        return lessonId;
    }

    public void setLessonId(UUID lessonId) {
        this.lessonId = lessonId;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
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

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public String getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    @Override
    public String toString() {
        return "LessonEntity{" +
                "lessonId=" + lessonId +
                ", course=" + (course != null ? course.getCourseId() : null) +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoFileName='" + videoFileName + '\'' +
                ", documentLink='" + documentLink + '\'' +
                ", pdfFileName='" + pdfFileName + '\'' +
                '}';
    }
}
