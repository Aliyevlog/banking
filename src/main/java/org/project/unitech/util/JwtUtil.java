package org.project.unitech.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${unitech.app.jwtSecret}")
    private String jwtSecret;

    @Value("${unitech.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private String base64Key;
    private SecretKey key;

    @PostConstruct
    public void init() {
        base64Key = Base64.getEncoder().encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
        key = Keys.hmacShaKeyFor(base64Key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String pin) {
        return Jwts.builder()
                .subject(pin)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration((new Date(new Date().getTime() + jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Boolean validateToken(String token, String pin) {
        final String pinFromToken = getPinFromToken(token);
        return (pin.equals(pinFromToken) && !isTokenExpired(token));
    }

    public String getPinFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
