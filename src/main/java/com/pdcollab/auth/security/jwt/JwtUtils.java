package com.pdcollab.auth.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdcollab.auth.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl usePrincipal) {
        return generateTokenFromUsername(usePrincipal.getUsername(), usePrincipal.getId());
    }

    public String generateTokenFromUsername(String username, Long Id) {
        JwtDataModel jwtDataModel = new JwtDataModel(username, Id);
        ObjectMapper obj = new ObjectMapper();

        String jsonStr = null;
        try {
            jsonStr = obj.writeValueAsString(jwtDataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.builder().setSubject(jsonStr).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public String getUserNameFromJwtToken(String token) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonJwtData = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        JwtDataModel jwtDataModel = objectMapper.readValue(jsonJwtData, JwtDataModel.class);
        return jwtDataModel.getUsername();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            System.out.println(e);
        }
        return false;
    }

}
