package com.fit.studentservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SuccessResponse {
    private int statusCode;
    private String message;
}
