package com.example.lms.exceptions;

public class DuplicateEnrollmentException extends RuntimeException{
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
