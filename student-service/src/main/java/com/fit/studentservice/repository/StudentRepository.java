package com.fit.studentservice.repository;

import com.fit.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByClassId(String classId);
}
