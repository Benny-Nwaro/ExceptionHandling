package com.example.lms.enrollment;

import com.example.lms.courses.CourseEntity;
import com.example.lms.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByStudentId(Long studentId);
    Optional<EnrollmentEntity> findByStudentAndCourse(UserEntity student, CourseEntity course);

}

