package com.fit.lecturerservice.model.request;

import lombok.Data;

@Data
public class LecturerRequest {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
