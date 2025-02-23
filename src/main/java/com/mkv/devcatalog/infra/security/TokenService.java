package com.mkv.devcatalog.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mkv.devcatalog.domain.role.Role;
import com.mkv.devcatalog.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String secret;

    @Value("${api.security.token.duration}")
    private long duration;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API Dev Catalog")
                    .withSubject(user.getUsername())
                    .withClaim("roles", user.getRoles().stream().map(Role::getAuthority).toList())
                    .withClaim("userId", user.getId())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(duration))
                    .sign(algorithm);
        } catch(JWTCreationException e) {
            throw new RuntimeException("Error generating token:", e);
        }
    }

    public String verifyToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Dev Catalog")
                    .withClaimPresence("roles")
                    .withClaimPresence("userId")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Token JWT not valid or expired!");
        }
    }
}
