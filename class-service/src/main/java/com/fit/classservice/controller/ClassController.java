package com.fit.classservice.controller;

import com.fit.classservice.entity.Classes;
import com.fit.classservice.model.ClassRequest;
import com.fit.classservice.service.ClassService;
import com.fit.classservice.utils.PermissionService;
import com.fit.classservice.utils.SuccessResponse;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    private final PermissionService permissionService;
    private final RateLimiter rateLimiter;

    @GetMapping
    public ResponseEntity<List<Classes>> getAllClasses() {
        try {
            // Decorate the method call with RateLimiter
            List<Classes> classes = rateLimiter.executeSupplier(() -> classService.getAllClasses());
            return ResponseEntity.of(Optional.ofNullable(classes));
        } catch (Exception e) {
            // Handle the case where RateLimiter does not permit the method call
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @GetMapping("/exists/{classId}")
    public ResponseEntity<?> classExists(@PathVariable String classId) {
        return ResponseEntity.ok(classService.classExists(classId));
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody ClassRequest request, @RequestHeader("Authorization") String token) {
        // call service user with api checkPermission
        if (!permissionService.checkPermission(token,  List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        classService.createClass(request.getClassId(), request.getName(), request.getLecturerId());

        return ResponseEntity
                .created(null)
                .body(new SuccessResponse(201, "Class created"));
    }

    @PutMapping
    public ResponseEntity<?> updateClass(@RequestBody ClassRequest request, @RequestHeader("Authorization") String token) {
        // call service user with api checkPermission
        if (!permissionService.checkPermission(token,  List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        classService.updateClass(request.getClassId(), request.getName(), request.getLecturerId());

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Class updated"));
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteClass(@PathVariable String classId, @RequestHeader("Authorization") String token) {
        // call service user with api checkPermission
        if (!permissionService.checkPermission(token,  List.of("ADMIN"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        classService.deleteClass(classId);

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Class deleted"));
    }
}
