package com.offlixtrade.crypto.config;

import com.offlixtrade.crypto.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    private static final SecretKey key = getSecretKeyFromBase64(JwtConstant.SECRET_KEY);

    public static SecretKey getSecretKeyFromBase64(String base64Key) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String key() {
        return "OK";
    }

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        token = token.substring(7);
        Claims claims = (Claims) Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return String.valueOf(claims.get("email"));

    }


    public static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }

        return String.join(",", auth);
    }
}
