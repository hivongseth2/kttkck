package com.fit.classservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classes {
    @Id
    private String classId;
    private String name;
    private String lecturerId;
    private List<String> studentIds;

    public Classes(String classId, String name, String lecturerId) {
        this.classId = classId;
        this.name = name;
        this.lecturerId = lecturerId;
        studentIds = new ArrayList<String>();
    }

    public Classes(String classId, String name) {
        this.classId = classId;
        this.name = name;
        studentIds = new ArrayList<String>();
    }

    public void addStudent(String studentId) {
        studentIds.add(studentId);
    }

    public void removeStudent(String studentId) {
        studentIds.remove(studentId);
    }
}
