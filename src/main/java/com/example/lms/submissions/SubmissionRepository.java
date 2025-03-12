package com.example.lms.submissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, UUID> {

    List<SubmissionEntity> findByStudent(UserEntity student);

    List<SubmissionEntity> findByAssignment(AssignmentEntity assignment);

    boolean existsByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);

    SubmissionEntity findByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);

    Page<SubmissionEntity> findByStudentId(UUID studentId, Pageable pageable);

}
