package com.demo.shopapp.components;

import com.demo.shopapp.Exception.InvalidParamException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration; // save to an environment variable
    @Value("${jwt.secretKey}")
    private  String secretKey;

    public String generateToken(com.demo.shopapp.model.User user) throws Exception {
        //properties => claims
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());

        claims.put("roles" , user.getRole());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
          throw new InvalidParamException("Cannot create jwt token, error" + e.getMessage());
//            return null;
        }
    }

    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
//    private String generateSecretKey() {
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[64];
//        random.nextBytes(keyBytes);
//        return Encoders.BASE64.encode(keyBytes);
//    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    // check expiration
    public boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    public String extractPhoneNumber(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractPhoneNumber(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public List extractRoles(String token) {
        return extractClaims(token, claims -> claims.get("roles", List.class));
    }
}
