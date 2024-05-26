package com.fit.lecturerservice.service.impl;

import com.fit.lecturerservice.entity.Lecturer;
import com.fit.lecturerservice.exception.BadRequestException;
import com.fit.lecturerservice.model.request.CheckPermissionRequest;
import com.fit.lecturerservice.model.request.LecturerRequest;
import com.fit.lecturerservice.model.request.UserRequest;
import com.fit.lecturerservice.repository.LecturerRepository;
import com.fit.lecturerservice.service.LecturerService;
import com.fit.lecturerservice.utils.SuccessResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    public Lecturer getLecturerById(String id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException(400, "Lecturer not found");
        });

        return lecturer;
    }

    @Override
    public boolean lecturerExists(String id) {
        return lecturerRepository.existsById(id);
    }

    @Override
    @Transactional
    public void createLecturer(LecturerRequest request) {
        // check full info
        if (request.getId() == null || request.getName() == null || request.getEmail() == null
                || request.getPhone() == null || request.getAddress() == null) {
            throw new BadRequestException(400, "Missing information");
        }

        // check if lecturer exists
        if (lecturerExists(request.getId())) {
            throw new BadRequestException(400, "Lecturer already exists");
        }

        Lecturer lecturer = new Lecturer();
        lecturer.setId(request.getId());
        lecturer.setName(request.getName());
        lecturer.setEmail(request.getEmail());
        lecturer.setPhone(request.getPhone());
        lecturer.setAddress(request.getAddress());

        lecturerRepository.save(lecturer);

        // call api to create lecturer in user-service
        String url = "http://user-service:8001/api/user/create-user";

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(request.getId());
        userRequest.setPassword(request.getId());
        userRequest.setListRole(List.of("LECTURER"));

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
    public void updateLecturer(LecturerRequest request) {
        Lecturer lecturer = lecturerRepository.findById(request.getId()).orElseThrow(() -> {
            throw new BadRequestException(400, "Lecturer not found");
        });

        if (request.getName() != null && !request.getName().equals(lecturer.getName())) {
            lecturer.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().equals(lecturer.getEmail())) {
            lecturer.setEmail(request.getEmail());
        }

        if (request.getPhone() != null && !request.getPhone().equals(lecturer.getPhone())) {
            lecturer.setPhone(request.getPhone());
        }

        if (request.getAddress() != null && !request.getAddress().equals(lecturer.getAddress())) {
            lecturer.setAddress(request.getAddress());
        }

        lecturerRepository.save(lecturer);
    }

    @Override
    public void deleteLecturer(String id) {
        if (!lecturerExists(id)) {
            throw new BadRequestException(400, "Lecturer not found");
        }

        lecturerRepository.deleteById(id);
    }
}
