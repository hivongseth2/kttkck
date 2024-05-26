package com.fit.classservice.service.impl;

import com.fit.classservice.entity.Classes;
import com.fit.classservice.exception.BadRequestException;
import com.fit.classservice.repository.ClassRepository;
import com.fit.classservice.service.ClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<Classes> getAllClasses() {
        return classRepository.findAll();
    }

    private void checkLecturerExists(String lecturerId) {
        String url = "http://lecturer-service:8003/api/lecturers/exists/" + lecturerId;
        try {
            Boolean exists = restTemplate.getForObject(url, Boolean.class);
            if (exists == null || !exists) {
                throw new BadRequestException(400, "Lecturer not found");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log the response body for more details
            System.err.println("HTTP error response: " + e.getResponseBodyAsString());
            throw new BadRequestException(400, "Lecturer service error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            // Handle connection issues
            System.err.println("Resource access exception: " + e.getMessage());
            throw new BadRequestException(400, "Lecturer service connection error: " + e.getMessage());
        } catch (Exception e) {
            // Log generic exceptions
            System.err.println("General error: " + e.getMessage());
            throw new BadRequestException(400, "Lecturer service error");
        }
    }

    @Override
    public void createClass(String classId, String name, String lecturerId) {
        // check info
        if (classId == null || name == null || lecturerId == null) {
            throw new BadRequestException(400, "ClassId, name, lecturerId must not be null");
        }

        // Check if lecturerId exists
        checkLecturerExists(lecturerId);

        // create class
        Classes classes = new Classes(classId, name, lecturerId);
        classRepository.save(classes);
    }

    @Override
    public void updateClass(String classId, String name, String lecturerId) {
        Classes classes = classRepository.findById(classId).orElseThrow(
                () -> new BadRequestException(400, "Class not found")
        );

        if (name != null && !name.equals(classes.getName())) {
            classes.setName(name);
        }

        if (lecturerId != null && !lecturerId.equals(classes.getLecturerId())) {
            // check lecturerId exists
            checkLecturerExists(lecturerId);

            // update lecturerId
            classes.setLecturerId(lecturerId);
        }

        classRepository.save(classes);
    }

    @Override
    public void addStudent(String classId, String studentId) {
        if (classId == null || studentId == null) {
            throw new BadRequestException(400, "ClassId, studentId must not be null");
        }

        Classes classes = classRepository.findById(classId).orElseThrow(
                () -> new BadRequestException(400, "Class not found")
        );

        // check studentId exists

        // add student
        classes.getStudentIds().add(studentId);
        classRepository.save(classes);

        // set student's classId
    }

    @Override
    @Transactional
    public void removeStudent(String classId, String studentId) {
        if (classId == null || studentId == null) {
            throw new BadRequestException(400, "ClassId, studentId must not be null");
        }

        Classes classes = classRepository.findById(classId).orElseThrow(
                () -> new BadRequestException(400, "Class not found")
        );

        // remove student
        classes.getStudentIds().remove(studentId);
        classRepository.save(classes);

        // set student's classId to null
    }

    @Override
    public void deleteClass(String classId) {
        Classes classes = classRepository.findById(classId).orElseThrow(
                () -> new BadRequestException(400, "Class not found")
        );

        classRepository.delete(classes);
    }
}
