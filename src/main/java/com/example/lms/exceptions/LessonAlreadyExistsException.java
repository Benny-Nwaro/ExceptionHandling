package com.example.lms.exceptions;

public class LessonAlreadyExistsException extends RuntimeException {
    public LessonAlreadyExistsException(String message) {
        super(message);
    }
}
