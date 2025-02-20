package com.example.lms.repositories;

import com.example.lms.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionsRepository extends JpaRepository<SubmissionEntity, Long> {
    List<SubmissionEntity> findByStudentUserId(Long studentId);
    List<SubmissionEntity> findByAssignmentAssignmentId(Long assignmentId);
}

