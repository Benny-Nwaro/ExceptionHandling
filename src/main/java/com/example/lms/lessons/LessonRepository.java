package com.example.lms.lessons;


import com.example.lms.courses.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByCourse_CourseId(Long courseId);
    Optional<LessonEntity> findByCourseAndTitle(CourseEntity course, String title);

}

