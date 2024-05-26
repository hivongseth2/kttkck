package com.fit.studentservice.model.request;

import lombok.Data;

@Data
public class StudentRequest {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String classId;
}
