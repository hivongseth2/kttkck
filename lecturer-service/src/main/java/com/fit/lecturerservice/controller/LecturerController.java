package com.fit.lecturerservice.controller;

import com.fit.lecturerservice.model.request.LecturerRequest;
import com.fit.lecturerservice.service.LecturerService;
import com.fit.lecturerservice.utils.PermissionService;
import com.fit.lecturerservice.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturers")
@RequiredArgsConstructor
public class LecturerController {
    private final LecturerService lecturerService;
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> getAllLecturers(@RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        return ResponseEntity.ok(lecturerService.getAllLecturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLecturerById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        return ResponseEntity.ok(lecturerService.getLecturerById(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<?> lecturerExists(@PathVariable String id) {
        return ResponseEntity.ok(lecturerService.lecturerExists(id));
    }

    @PostMapping
    public ResponseEntity<?> createLecturer(@RequestBody LecturerRequest request, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        lecturerService.createLecturer(request);
        return ResponseEntity.ok(new SuccessResponse(200, "Lecturer created successfully"));
    }

    @PutMapping
    public ResponseEntity<?> updateLecturer(@RequestBody LecturerRequest request, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        lecturerService.updateLecturer(request);
        return ResponseEntity.ok(new SuccessResponse(200, "Lecturer updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLecturer(@PathVariable String id, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        lecturerService.deleteLecturer(id);
        return ResponseEntity.ok(new SuccessResponse(200, "Lecturer deleted successfully"));
    }
}
