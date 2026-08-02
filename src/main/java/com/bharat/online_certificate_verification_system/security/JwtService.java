package com.bharat.online_certificate_verification_system.security;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.expiration}")
        private long jwtExpiration;

        private SecretKey secretKey(){
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public String generateToken(UserDetails  userDetails){
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis()+ jwtExpiration))
                    .signWith(secretKey())
                    .compact();
        }

//        step1
        private Claims extractAllClaims(String token){
            return Jwts.parser()
                    .verifyWith(secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
//        step 2
        public <T> T extractClaim(String token,Function<Claims, T>claimResolver){
            Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        }

//        step 3

        public String extractUsername(String token){
            return extractClaim(token, Claims::getSubject);
        }
//        step4
        public Date extractExpiration(String token){
            return extractClaim(token,Claims::getExpiration);
        }
//        step5
        private boolean isTokenExpired(String token){
            return extractExpiration(token).before(new Date());
        }
//        step6

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username= extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

}
