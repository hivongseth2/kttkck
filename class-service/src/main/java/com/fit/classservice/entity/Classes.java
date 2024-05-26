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

    public Classes(String classId, String name) {
        this.classId = classId;
        this.name = name;
    }
}
