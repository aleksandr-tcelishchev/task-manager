package com.netcracker.taskmanager.security;

import com.netcracker.taskmanager.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Component
@Log
public class JwtProvider {
    private final String SECRET = "xe-xe";
    private UserService userService;

    public JwtProvider(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(String email){
        Date date = Date.from(LocalDate.now().plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("email",email);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return true;
        }catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.get("email").toString();
    }
}
