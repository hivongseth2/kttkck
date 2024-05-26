package com.fit.lecturerservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckPermissionRequest {
    @JsonProperty("token")
    private String token;

    @JsonProperty("roles")
    private List<String> roles;
}
