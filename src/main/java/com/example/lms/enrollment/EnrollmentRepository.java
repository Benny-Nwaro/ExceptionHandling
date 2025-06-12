package com.example.lms.enrollment;

import com.example.lms.courses.CourseEntity;
import com.example.lms.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {
    List<EnrollmentEntity> findByStudentId(UUID studentId);

    Optional<EnrollmentEntity> findByStudentAndCourse(UserEntity student, CourseEntity course);
    @Query("SELECT COUNT(e) > 0 FROM EnrollmentEntity e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    boolean isStudentEnrolled(@Param("studentId") UUID studentId, @Param("courseId") UUID courseId);
    @Query("SELECT e.student FROM EnrollmentEntity e WHERE e.course.id = :courseId")
    List<UserEntity> findUsersByCourseId(@Param("courseId") UUID courseId);

    @Query("SELECT e.student FROM EnrollmentEntity e WHERE e.course.instructorId = :instructorId")
    List<UserEntity> findStudentsByInstructorId(@Param("instructorId") UUID instructorId);


}

