package com.fit.classservice.service;

import com.fit.classservice.entity.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService {
    List<Classes> getAllClasses();

    void createClass(String classId, String name, String lecturerId);

    void updateClass(String classId, String name, String lecturerId);

    void addStudent(String classId, String studentId);

    void removeStudent(String classId, String studentId);

    void deleteClass(String classId);
}
