package com.fit.userservice.utils;

import com.fit.userservice.exception.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authen {

    public static void check(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Username: " + username);
        if(username == null || username.equals("anonymousUser")){
            throw new BadRequestException(400, "User is not logged in!");
        }
    }

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username == null || username.equals("anonymousUser")){
            throw new BadRequestException(400, "User is not logged in!");
        }

        return username;
    }
}

