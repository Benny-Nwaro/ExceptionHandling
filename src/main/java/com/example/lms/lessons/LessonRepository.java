package com.example.lms.lessons;


import com.example.lms.courses.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByCourse_CourseId(Long courseId);
    Boolean findByCourseAndTitle(CourseEntity course, String title);

}

