package com.example.lms.courses;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByTitle(String title);
    List<CourseEntity> findByInstructorId(UUID instructorId);
    boolean existsByTitle(String title);


}

