package com.fit.userservice.controller;

import com.fit.userservice.model.request.CheckPermissionRequest;
import com.fit.userservice.model.request.UserRequest;
import com.fit.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        userService.createUser(request);

        return ResponseEntity
                .created(null)
                .body("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.login(request.getUsername(), request.getPassword(), "login"));
    }

    @PostMapping("/check-permission")
    public Boolean checkPermission(@RequestBody CheckPermissionRequest request) {
        System.out.println("Check permission: " + request.getToken());
        boolean result = userService.checkPermission(request);
        System.out.println("Check permission result: " + result);

        return result;
    }
}
