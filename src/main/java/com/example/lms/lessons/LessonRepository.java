package com.example.lms.lessons;


import com.example.lms.courses.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findByCourse_CourseId(UUID courseId);
    List<LessonEntity> findByCourse(CourseEntity course);
    Optional<LessonEntity> findByCourseAndTitle(CourseEntity course, String title);

}

