package com.example.lms.services;


import com.example.lms.entities.LessonEntity;
import com.example.lms.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    public LessonEntity getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public LessonEntity saveLesson(LessonEntity lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}

