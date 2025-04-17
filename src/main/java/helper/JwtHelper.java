package helper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String secret;

    private static Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    public static String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = getTokenBody(token);
            return userDetails.getUsername().equals(claims.getSubject()) && !isTokenExpired(claims);
        } catch (JwtException e) {
            return false;
        }
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
