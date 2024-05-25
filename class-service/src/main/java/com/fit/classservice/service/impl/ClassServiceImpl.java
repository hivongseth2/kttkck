package com.fit.classservice.service.impl;

import com.fit.classservice.entity.Classes;
import com.fit.classservice.exception.BadRequestException;
import com.fit.classservice.repository.ClassRepository;
import com.fit.classservice.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    @Override
    public List<Classes> getAllClasses() {
        return classRepository.findAll();
    }

    @Override
    public void createClass(String classId, String name, String lecturerId) {
        validateInputs(classId, name, lecturerId);

        // Check if lecturerId exists (implementation needed)

        Classes classes = new Classes(classId, name, lecturerId);
        classRepository.save(classes);
    }

    @Override
    public void updateClass(String classId, String name, String lecturerId) {
        Classes classes = getClassById(classId);

        if (name != null && !name.equals(classes.getName())) {
            classes.setName(name);
        }

        if (lecturerId != null && !lecturerId.equals(classes.getLecturerId())) {
            // Check if lecturerId exists (implementation needed)

            classes.setLecturerId(lecturerId);
        }

        classRepository.save(classes);
    }

    @Override
    public void addStudent(String classId, String studentId) {
        validateInputs(classId, studentId);

        Classes classes = getClassById(classId);

        // Check if studentId exists (implementation needed)

        classes.getStudentIds().add(studentId);
        classRepository.save(classes);
    }

    @Override
    public void removeStudent(String classId, String studentId) {
        validateInputs(classId, studentId);

        Classes classes = getClassById(classId);
        classes.getStudentIds().remove(studentId);
        classRepository.save(classes);
    }

    @Override
    public void deleteClass(String classId) {
        classRepository.deleteById(classId);
    }

    // Helper Methods

    private Classes getClassById(String classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new BadRequestException(400, "Class not found"));
    }

    private void validateInputs(String classId, String name, String lecturerId) {
        if (classId == null || name == null || lecturerId == null) {
            throw new BadRequestException(400, "ClassId, name, lecturerId must not be null");
        }
    }

    private void validateInputs(String classId, String studentId) {
        if (classId == null || studentId == null) {
            throw new BadRequestException(400, "ClassId, studentId must not be null");
        }
    }
}
