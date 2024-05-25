package com.fit.classservice.controller;

import com.fit.classservice.entity.Classes;
import com.fit.classservice.model.ClassRequest;
import com.fit.classservice.service.ClassService;
import com.fit.classservice.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<List<Classes>> getAllClasses() {
        List<Classes> classes = classService.getAllClasses();

        return ResponseEntity.of(Optional.ofNullable(classes));
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody ClassRequest request) {
        classService.createClass(request.getClassId(), request.getName(), request.getLecturerId());

        return ResponseEntity
                .created(null)
                .body(new SuccessResponse(201, "Class created"));
    }

    @PutMapping
    public ResponseEntity<?> updateClass(@RequestBody ClassRequest request) {
        classService.updateClass(request.getClassId(), request.getName(), request.getLecturerId());

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Class updated"));
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteClass(@PathVariable String classId) {
        classService.deleteClass(classId);

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Class deleted"));
    }

    @PostMapping("/{classId}/student/add")
    public ResponseEntity<?> addStudent(@PathVariable String classId, @RequestParam String studentId) {
        classService.addStudent(classId, studentId);

        return ResponseEntity
                .created(null)
                .body(new SuccessResponse(201, "Student added"));
    }

    @DeleteMapping("/{classId}/student/remove")
    public ResponseEntity<?> removeStudent(@PathVariable String classId, @RequestParam String studentId) {
        classService.removeStudent(classId, studentId);

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Student removed"));
    }
}
