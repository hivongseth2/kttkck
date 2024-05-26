package com.fit.lecturerservice.service;

import com.fit.lecturerservice.entity.Lecturer;
import com.fit.lecturerservice.model.request.LecturerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LecturerService {
    List<Lecturer> getAllLecturers();

    Lecturer getLecturerById(String id);

    boolean lecturerExists(String id);

    void createLecturer(LecturerRequest request);

    void updateLecturer(LecturerRequest request);

    void deleteLecturer(String id);
}
