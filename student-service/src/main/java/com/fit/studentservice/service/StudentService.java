package com.fit.studentservice.service;

import com.fit.studentservice.entity.Student;
import com.fit.studentservice.model.request.StudentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getAllStudents();

    boolean studentExists(String id);

    void createStudent(StudentRequest request);

    void updateStudent(StudentRequest request);

    void deleteStudent(String id);

    List<Student> getStudentsByClass(String classId);
}
