package com.example.lms.services;


import com.example.lms.entities.EnrollmentEntity;
import com.example.lms.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<EnrollmentEntity> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public EnrollmentEntity getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    public List<EnrollmentEntity> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public EnrollmentEntity saveEnrollment(EnrollmentEntity enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}

