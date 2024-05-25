package com.fit.userservice.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public abstract class JwtService<T> {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(String data) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + expiration;
        System.out.println(expirationTime);
        return Jwts.builder()
                .setSubject(data)
                .claim("data", data)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired");
            return false;
        } catch (MalformedJwtException e){
            log.error("Token malformed");
            return false;
        } catch (UnsupportedJwtException e){
            log.error("Token unsupported");
            return false;
        } catch (IllegalArgumentException e){
            log.error("Token illegal argument");
            return false;
        }
    }

    public String getUsernameFromToken(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public Claims decodeToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}

