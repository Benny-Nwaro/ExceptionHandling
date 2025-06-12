package com.example.lms.exceptions;

public class DuplicateSubmissionException extends RuntimeException {
    public DuplicateSubmissionException(String message) {
        super(message);
    }
}
