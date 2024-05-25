package com.fit.classservice.controller;

import com.fit.classservice.entity.Classes;
import com.fit.classservice.model.ClassRequest;
import com.fit.classservice.service.ClassService;
import com.fit.classservice.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<List<Classes>> getAllClasses() {
        List<Classes> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createClass(@RequestBody ClassRequest request) {
        classService.createClass(request.getClassId(), request.getName(), request.getLecturerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(201, "Class created"));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> updateClass(@RequestBody ClassRequest request) {
        classService.updateClass(request.getClassId(), request.getName(), request.getLecturerId());
        return ResponseEntity.ok(new SuccessResponse(200, "Class updated"));
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<SuccessResponse> deleteClass(@PathVariable String classId) {
        classService.deleteClass(classId);
        return ResponseEntity.ok(new SuccessResponse(200, "Class deleted"));
    }

    @PostMapping("/{classId}/student/add")
    public ResponseEntity<SuccessResponse> addStudent(@PathVariable String classId, @RequestParam String studentId) {
        classService.addStudent(classId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(201, "Student added"));
    }

    @DeleteMapping("/{classId}/student/remove")
    public ResponseEntity<SuccessResponse> removeStudent(@PathVariable String classId, @RequestParam String studentId) {
        classService.removeStudent(classId, studentId);
        return ResponseEntity.ok(new SuccessResponse(200, "Student removed"));
    }
}
