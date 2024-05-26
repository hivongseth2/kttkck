package com.fit.studentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_student")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String classId;
}
