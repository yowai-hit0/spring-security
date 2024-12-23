package com.example.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class JwtService {
    private static final String SECRET_KEY = "99048488857D39724AD9C472A128B120CB74B568A2693E35596D98E7A0A4504A116845FFB73443C24F163749686461E23F45335E2F11A4FAF1375E1F038BE41F05B1F7921011A6B57667A101A2F009B48DFF460BE4486F131D574C174762969C9FD3A35102E672DFAE48B26F222E49278A73E061463EE6D04A3C1A42AA2FC8C8";

    public String extractUsername(String token) {
        return  extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Objects> ExtraClaims, UserDetails user){
        return Jwts.builder()
                .setClaims(ExtraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
    public String generateToken( UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
