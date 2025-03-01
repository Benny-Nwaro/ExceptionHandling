package com.example.lms.submissions;

import com.example.lms.assignments.AssignmentEntity;
import com.example.lms.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Long> {

    // Find all submissions by a specific student
    List<SubmissionEntity> findByStudent(UserEntity student);

    // Find all submissions for a specific assignment
    List<SubmissionEntity> findByAssignment(AssignmentEntity assignment);

    // Check if a student has already submitted an assignment
    boolean existsByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);

    // Find a submission by student and assignment (if unique per student)
    SubmissionEntity findByStudentAndAssignment(UserEntity student, AssignmentEntity assignment);
}
