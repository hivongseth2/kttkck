package com.fit.studentservice.service.impl;

import com.fit.studentservice.entity.Student;
import com.fit.studentservice.exception.BadRequestException;
import com.fit.studentservice.model.request.StudentRequest;
import com.fit.studentservice.model.request.UserRequest;
import com.fit.studentservice.repository.StudentRepository;
import com.fit.studentservice.service.StudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public boolean studentExists(String id) {
        return studentRepository.existsById(id);
    }

    private void checkClassExists(String classId) {
        String url = "http://class-service:8002/api/class/exists/" + classId;
        try {
            Boolean exists = restTemplate.getForObject(url, Boolean.class);
            if (exists == null || !exists) {
                throw new BadRequestException(400, "Class not found");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log the response body for more details
            System.err.println("HTTP error response: " + e.getResponseBodyAsString());
            throw new BadRequestException(400, "Class service error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            // Handle connection issues
            System.err.println("Resource access exception: " + e.getMessage());
            throw new BadRequestException(400, "Class service connection error: " + e.getMessage());
        } catch (Exception e) {
            // Log generic exceptions
            System.err.println("General error: " + e.getMessage());
            throw new BadRequestException(400, "Class service error: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public void createStudent(StudentRequest request) {
        if (request.getId() == null || request.getName() == null || request.getEmail() == null
                || request.getPhone() == null || request.getAddress() == null || request.getClassId() == null) {
            throw new BadRequestException(400, "Missing information");
        }

        if (studentExists(request.getId())) {
            throw new BadRequestException(400, "Student already exists");
        }

        checkClassExists(request.getClassId());

        Student student = new Student();
        student.setId(request.getId());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());
        student.setClassId(request.getClassId());

        studentRepository.save(student);

        // call api to create lecturer in user-service
        String url = "http://user-service:8001/api/user/create-user";

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(request.getId());
        userRequest.setPassword(request.getId());
        userRequest.setListRole(List.of("STUDENT"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest, headers);

        try {
            String response = restTemplate.postForObject(url, entity, String.class);
            System.out.println("Response: " + response);
        } catch (BadRequestException e) {
            throw new BadRequestException(400, e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(400, e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateStudent(StudentRequest request) {
        Student student = studentRepository.findById(request.getId()).orElseThrow(() -> {
            throw new BadRequestException(400, "Student not found");
        });

        if (request.getName() != null && !request.getName().equals(student.getName())) {
            student.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            student.setEmail(request.getEmail());
        }

        if (request.getPhone() != null && !request.getPhone().equals(student.getPhone())) {
            student.setPhone(request.getPhone());
        }

        if (request.getAddress() != null && !request.getAddress().equals(student.getAddress())) {
            student.setAddress(request.getAddress());
        }

        if (request.getClassId() != null && !request.getClassId().equals(student.getClassId())) {
            checkClassExists(request.getClassId());
            student.setClassId(request.getClassId());
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String id) {
        if (!studentExists(id)) {
            throw new BadRequestException(400, "Student not found");
        }

        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByClass(String classId) {
        return studentRepository.findByClassId(classId);
    }
}
