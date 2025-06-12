package com.example.lms.exceptions;

public class EnrollmentNotFoundException extends RuntimeException{
    public EnrollmentNotFoundException(String message) {
        super(message);
    }
}
