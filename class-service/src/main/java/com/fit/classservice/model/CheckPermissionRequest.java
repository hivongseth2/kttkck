package com.fit.classservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class CheckPermissionRequest {
    @JsonProperty("token")
    private String token;

    @JsonProperty("roles")
    private List<String> roles;
}
