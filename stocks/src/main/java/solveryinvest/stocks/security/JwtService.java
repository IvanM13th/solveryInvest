package solveryinvest.stocks.security;


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
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.Role;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component("jwt-service")
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

    public Authentication getAuthentication(String token) {
        var id = getUserId(token);
        var role = getRole(token);
        var email = getEmail(token);
        User principal = new User(id, role, email, token);
        var pr = getPrincipal(token);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private User getPrincipal(String token) {
        var claims = getTokenBody(token);
        var id = (long) (int) claims.getOrDefault("id", 1L);
        var role = Role.valueOf((String) claims.getOrDefault("role", Role.GUEST));
        var name = (String) claims.getOrDefault("firstName", "");
        var lastName = (String) claims.getOrDefault("lastName", "");
        var email = (String) claims.getOrDefault("email", "");
        var password = (String) claims.getOrDefault("password", "");
        return User.builder().id(id).firstName(name).lastName(lastName).email(email).password(password).role(role).build();
    }

    public Long getUserId(String token) {
        var claims = getTokenBody(token);
        var id = claims.getOrDefault("id", 1L);
        return (long) (int) id;
    }

    public String getEmail(String token) {
        var claims = getTokenBody(token);
        return (String) claims.getOrDefault("email", "");
    }

    public Role getRole(String token) {
        var claims = getTokenBody(token);
        return Role.valueOf((String) claims.getOrDefault("role", Role.GUEST));
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

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
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
