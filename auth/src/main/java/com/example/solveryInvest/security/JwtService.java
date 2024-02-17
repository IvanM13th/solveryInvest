package com.example.solveryInvest.security;

import com.example.solveryInvest.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component("jwt-s")
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.key}")
    private String signingKey;

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        var key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }
    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long getUserId(String token) {
        var claims = getTokenBody(token);
        var id = claims.get("id");
        return (long) (int) id;
    }

    public String generateToken(User user) {
        Map<String, Object> map = Map.of(
                "id",user.getId(),
                "role", user.getRole(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail());
        return generateToken(map, user);
    }

    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 12))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims getTokenBody(String jwt) {
        return jwtParser.parseClaimsJws(jwt).getBody();
    }
}
