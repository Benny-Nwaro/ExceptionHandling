package com.example.lms.assignments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, UUID> {
    boolean existsByTitle(String title);
}
