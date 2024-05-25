package com.fit.userservice.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtils {

    private static final  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // Hash a plain text password
    public static String hashPassword(String plainTextPassword) {
        return bCryptPasswordEncoder.encode(plainTextPassword);
    }

    // Verify a plain text password against a hashed password
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(plainTextPassword, hashedPassword);
    }
}
