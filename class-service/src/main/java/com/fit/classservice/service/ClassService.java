package com.fit.classservice.service;

import com.fit.classservice.entity.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService {
    List<Classes> getAllClasses();

    boolean classExists(String classId);

    void createClass(String classId, String name, String lecturerId);

    void updateClass(String classId, String name, String lecturerId);

    void deleteClass(String classId);
}
