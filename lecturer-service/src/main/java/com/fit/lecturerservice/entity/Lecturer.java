package com.fit.lecturerservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_lecturer")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Lecturer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
