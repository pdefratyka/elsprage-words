package com.elsprage.words.tools.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class TokenService {

    private static final String SECRET_KEY = "763979244226452948404D6251655468576D5A7134743777217A25432A462D4A";

    public static String generateToken() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1L);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject("userName")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
