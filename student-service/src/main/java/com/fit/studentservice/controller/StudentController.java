package com.fit.studentservice.controller;

import com.fit.studentservice.entity.Student;
import com.fit.studentservice.model.request.StudentRequest;
import com.fit.studentservice.service.StudentService;
import com.fit.studentservice.utils.PermissionService;
import com.fit.studentservice.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN", "LECTURER"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        List<Student> students = studentService.getAllStudents();

        return ResponseEntity.of(Optional.ofNullable(students));
    }

    @GetMapping("/student-by-class/{classId}")
    public ResponseEntity<?> getStudentsByClass(@PathVariable String classId, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN", "LECTURER"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        List<Student> students = studentService.getStudentsByClass(classId);

        return ResponseEntity.of(Optional.ofNullable(students));
    }

    @GetMapping("/exists/{studentId}")
    public ResponseEntity<?> studentExists(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.studentExists(studentId));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest request, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN", "LECTURER"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        studentService.createStudent(request);

        return ResponseEntity
                .created(null)
                .body(new SuccessResponse(201, "Student created"));
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody StudentRequest request, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN", "LECTURER"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        studentService.updateStudent(request);

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Student updated"));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId, @RequestHeader("Authorization") String token) {
        if (!permissionService.checkPermission(token, List.of("ADMIN", "LECTURER"))) {
            return ResponseEntity.status(403).body("Permission Denied");
        }

        studentService.deleteStudent(studentId);

        return ResponseEntity
                .ok()
                .body(new SuccessResponse(200, "Student deleted"));
    }
}
